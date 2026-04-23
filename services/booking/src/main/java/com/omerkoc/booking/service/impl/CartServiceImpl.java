package com.omerkoc.booking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.omerkoc.booking.client.CustomerClient;
import com.omerkoc.booking.client.FlightClient;
import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;
import com.omerkoc.booking.exception.CartExpiredException;
import com.omerkoc.booking.exception.CustomerNotFoundException;
import com.omerkoc.booking.exception.FlightNotFoundException;
import com.omerkoc.booking.mapper.CartMapper;
import com.omerkoc.booking.model.Cart;
import com.omerkoc.booking.model.CartItem;
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

        try {
            customerClient.getCustomerById(userId);
        } catch (Exception e) {
            log.error("Validation failed: User with ID {} not found in customer-service", userId);
            throw new CustomerNotFoundException("User not found with ID: " + userId);
        }

        try {
            flightClient.getFlightById(request.flightId());
        } catch (Exception e) {
            log.error("Validation failed: Flight with ID {} not found in flights-service", request.flightId());
            throw new FlightNotFoundException("Flight not found with ID: " + request.flightId());
        }

        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);
        if (cart == null) {
            cart = cartMapper.createNewCart(userId);
        }

        CartItem newItem = cartMapper.toCartItem(request);
        cart.getItems().add(newItem);

        redisTemplate.opsForValue().set(CART_PREFIX + userId, cart, CART_TTL, TimeUnit.MINUTES);
        log.info("Success: Flight {} added to cart for user {}. Total items: {}",
                request.flightId(), userId, cart.getItemCount());

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
    public CartResponseDto removeFromCart(String userId, Integer flightId) {
        log.info("Removing flight {} from cart for user {}", flightId, userId);

        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);

        if (cart == null) {
            log.warn("Cart for user {} has expired or does not exist", userId);
            return null;
        }

        boolean removed = cart.getItems().removeIf(item -> item.getFlightId().equals(flightId));

        if (!removed) {
            log.warn("Flight {} was not found in cart for user {}", flightId, userId);
        }

        if (cart.getItems().isEmpty()) {
            clearCart(userId);
            log.info("Cart is now empty for user {}, removing from Redis", userId);
            return null;
        }

        redisTemplate.opsForValue().set(CART_PREFIX + userId, cart, CART_TTL, TimeUnit.MINUTES);
        log.info("Flight {} removed from cart for user {}. Remaining items: {}",
                flightId, userId, cart.getItemCount());

        return cartMapper.toResponse(cart);
    }

    @Override
    public void clearCart(String userId) {
        log.info("Clearing Redis cart for user: {}", userId);
        redisTemplate.delete(CART_PREFIX + userId);
    }

    @Override
    public List<BookingResponseDto> checkout(String userId) {
        log.info("Initiating checkout for user: {}", userId);

        Cart cart = (Cart) redisTemplate.opsForValue().get(CART_PREFIX + userId);

        if (cart == null || cart.getItems().isEmpty()) {
            log.error("Checkout failed: Cart session expired or empty for user {}", userId);
            throw new CartExpiredException("Cart session expired or empty! Please select your flights again.");
        }

        List<BookingResponseDto> bookingResponses = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            BookingRequestDto bookingRequest = new BookingRequestDto(
                    cart.getUserId(),
                    item.getFlightId());

            BookingResponseDto response = bookingService.createBooking(bookingRequest);
            bookingResponses.add(response);
            log.info("Booking created for flight {} with PNR: {}",
                    item.getFlightId(), response.bookingCode());
        }

        clearCart(userId);
        log.info("Checkout completed successfully for user: {}. {} bookings created.",
                userId, bookingResponses.size());

        return bookingResponses;
    }
}