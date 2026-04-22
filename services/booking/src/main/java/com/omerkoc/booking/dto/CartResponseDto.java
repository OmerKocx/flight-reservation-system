package com.omerkoc.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record CartResponseDto(
        String message,
        String userId,
        List<CartItemDto> items,
        int itemCount,
        BigDecimal totalPrice,
        LocalDateTime expiresAt) {

    @Builder
    public record CartItemDto(
            Integer flightId,
            String seatNumber,
            BigDecimal price) {
    }
}