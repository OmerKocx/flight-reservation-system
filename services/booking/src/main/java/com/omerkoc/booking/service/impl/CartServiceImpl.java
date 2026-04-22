package com.omerkoc.booking.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.omerkoc.booking.client.CustomerClient;
import com.omerkoc.booking.client.FlightClient;
import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;
import com.omerkoc.booking.exception.CustomerNotFoundException; // Kendi exception paketine göre kontrol et
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

    private final FlightClient flightClient;
    private final CustomerClient customerClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final IBookingService bookingService;
    private final CartMapper cartMapper;

    private static final String CART_PREFIX = "cart:";
    private static final long CART_TTL = 10;

    @Override
    public CartResponseDto addToCart(String userId, CartRequestDto request) {
        log.info("Process started: Adding flight {} to cart for user {}", request.flightId(), userId);

        // STEP 0: USER VALIDATION
        try {
            customerClient.getCustomerById(userId);
        } catch (Exception e) {
            log.error("Validation failed: User with ID {} not found in customer-service", userId);
            throw new CustomerNotFoundException("User not found with ID: " + userId);
        }

        // STEP 0.1: FLIGHT VALIDATION
        try {
            flightClient.getFlightById(request.flightId());
        } catch (Exception e) {
            log.error("Validation failed: Flight with ID {} not found in flights-service", request.flightId());
            throw new RuntimeException("Flight not found with ID: " + request.flightId());
        }

        // STEP 1: Map to Redis Model
        Cart cart = cartMapper.toCart(userId, request);

        // STEP 2: Persist in Redis
        redisTemplate.opsForValue().set(CART_PREFIX + userId, cart, CART_TTL, TimeUnit.MINUTES);
        log.info("Success: Flight added to Redis cart for user {}", userId);

        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponseDto getCart(String userId) {
        log.info("Fetching cart for user: {}", userId);

        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);

        if (cart == null) {
            log.warn("Cart for user {} has expired or does not exist", userId);
            return null;
        }

        return cartMapper.toResponse(cart);
    }

    @Override
    public void clearCart(String userId) {
        log.info("Clearing Redis cart for user: {}", userId);
        redisTemplate.delete(CART_PREFIX + userId);
    }

    @Override
    public BookingResponseDto checkout(String userId) {
        log.info("Initiating checkout for user: {}", userId);

        // STEP 1: Check Redis Session
        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);

        if (cart == null) {
            log.error("Checkout failed: Cart session expired for user {}", userId);
            throw new RuntimeException("Cart session expired! Please select your flight again.");
        }

        // STEP 2: Prepare Booking Request
        BookingRequestDto bookingRequest = new BookingRequestDto(
                cart.getUserId(),
                cart.getFlightId());

        // STEP 3: Create Permanent Booking in DB
        BookingResponseDto response = bookingService.createBooking(bookingRequest);

        // STEP 4: Purge Cart on Success
        if (response != null) {
            clearCart(userId);
            log.info("Checkout completed successfully for user: {}", userId);
        }

        return response;
    }
}