package com.omerkoc.booking.service;

import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;

public interface ICartService {
    CartResponseDto addToCart(String userId, CartRequestDto request);

    CartResponseDto getCart(String userId);

    void clearCart(String userId);

    Object checkout(String userId);
}