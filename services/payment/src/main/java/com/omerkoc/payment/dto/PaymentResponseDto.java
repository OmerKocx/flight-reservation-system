package com.omerkoc.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.omerkoc.payment.model.PaymentMethod;
import com.omerkoc.payment.model.PaymentStatus;

import lombok.Builder;

@Builder
public record PaymentResponseDto(
        Long id,
        Long bookingId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        LocalDateTime createdAt
) {
}
