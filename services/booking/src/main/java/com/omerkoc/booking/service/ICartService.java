package com.omerkoc.booking.service;

import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;

import java.util.List;

import com.omerkoc.booking.dto.BookingResponseDto;

public interface ICartService {
    CartResponseDto addToCart(String userId, CartRequestDto request);

    CartResponseDto getCart(String userId);

    CartResponseDto removeFromCart(String userId, Integer flightId);

    void clearCart(String userId);

    List<BookingResponseDto> checkout(String userId);
}