package com.omerkoc.booking.mapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;
import com.omerkoc.booking.model.Cart;
import com.omerkoc.booking.model.CartItem;

@Component
public class CartMapper {

    public CartItem toCartItem(CartRequestDto request) {
        return CartItem.builder()
                .flightId(request.flightId())
                .seatNumber(request.seatNumber())
                .price(request.price())
                .build();
    }

    public Cart createNewCart(String userId) {
        return Cart.builder()
                .userId(userId)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();
    }

    public CartResponseDto toResponse(Cart cart) {
        return CartResponseDto.builder()
                .message("Sepet aktif - " + cart.getItemCount() + " bilet")
                .userId(cart.getUserId())
                .items(cart.getItems().stream()
                        .map(item -> CartResponseDto.CartItemDto.builder()
                                .flightId(item.getFlightId())
                                .seatNumber(item.getSeatNumber())
                                .price(item.getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .itemCount(cart.getItemCount())
                .totalPrice(cart.getTotalPrice())
                .expiresAt(cart.getExpiresAt())
                .build();
    }
}
