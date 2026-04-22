package com.omerkoc.booking.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;
import com.omerkoc.booking.mapper.CartMapper;
import com.omerkoc.booking.model.Cart;
import com.omerkoc.booking.service.IBookingService;
import com.omerkoc.booking.service.ICartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements ICartService {

    // Main tool for Redis read/write operations
    private final RedisTemplate<String, Object> redisTemplate;

    // Service responsible for persistent booking in PostgreSQL
    private final IBookingService bookingService;

    // Component to handle object conversions (DTO <-> Entity)
    private final CartMapper cartMapper;

    // Key prefix to prevent collisions in Redis (e.g., cart:user123)
    private static final String CART_PREFIX = "cart:";

    // Time-To-Live for the cart (10 minutes for flight seat reservation)
    private static final long CART_TTL = 10;

    @Override
    public CartResponseDto addToCart(String userId, CartRequestDto request) {
        log.info("Adding flight {} to cart for user {}", request.flightId(), userId);

        // STEP 1: Map the incoming request DTO to the Redis model (Cart)
        Cart cart = cartMapper.toCart(userId, request);

        // STEP 2: Persist the cart in Redis with a 10-minute expiration
        // If the user doesn't checkout within this time, Redis will auto-delete the key
        redisTemplate.opsForValue().set(CART_PREFIX + userId, cart, CART_TTL, TimeUnit.MINUTES);

        // STEP 3: Return the mapped response DTO to the client
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponseDto getCart(String userId) {
        log.info("Fetching cart from Redis for user: {}", userId);

        // Retrieve the raw object from Redis and cast it back to Cart model
        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);

        if (cart == null) {
            log.warn("Cart for user {} not found or has expired.", userId);
            return null;
        }

        // Map the existing Redis model to the response DTO
        return cartMapper.toResponse(cart);
    }

    @Override
    public void clearCart(String userId) {
        // Remove the cart key from Redis immediately
        log.info("Manually clearing cart for user: {}", userId);
        redisTemplate.delete(CART_PREFIX + userId);
    }

    @Override
    public BookingResponseDto checkout(String userId) {
        log.info("Starting checkout/confirmation process for user: {}", userId);

        // STEP 1: Check if the cart still exists in Redis (i.e., not expired)
        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);

        if (cart == null) {
            // Throw exception if the 10-minute lock window has closed
            throw new RuntimeException("Cart session expired! Please select your flight again.");
        }

        // STEP 2: Prepare the permanent booking request from temporary cart data
        // Converting 'Intent' to 'Official Reservation Request'
        BookingRequestDto bookingRequest = new BookingRequestDto(
                cart.getUserId(),
                cart.getFlightId());

        // STEP 3: Trigger the actual booking service to decrease capacity and save to DB
        // This generates the PNR code and persists data in PostgreSQL
        BookingResponseDto response = bookingService.createBooking(bookingRequest);

        // STEP 4: If the database transaction was successful, purge the temporary cart from Redis
        if (response != null) {
            clearCart(userId);
        }

        // Return the final booking details (PNR, seat, etc.) to the user
        return response;
    }
}