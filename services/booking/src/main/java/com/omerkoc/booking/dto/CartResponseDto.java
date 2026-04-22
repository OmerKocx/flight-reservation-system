package com.omerkoc.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CartResponseDto(
        String message,
        String userId,
        Integer flightId,
        String seatNumber,
        BigDecimal price,
        LocalDateTime expiresAt) {
}