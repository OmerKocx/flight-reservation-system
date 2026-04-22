package com.omerkoc.booking.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;
import com.omerkoc.booking.model.Cart;

@Component
public class CartMapper {

    public Cart toCart(String userId, CartRequestDto request) {
        return Cart.builder()
                .userId(userId)
                .flightId(request.flightId())
                .seatNumber(request.seatNumber())
                .price(request.price())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();
    }

    public CartResponseDto toResponse(Cart cart) {
        return CartResponseDto.builder()
                .message("Sepet aktif")
                .userId(cart.getUserId())
                .flightId(cart.getFlightId())
                .seatNumber(cart.getSeatNumber())
                .price(cart.getPrice())
                .expiresAt(cart.getExpiresAt())
                .build();
    }
}
