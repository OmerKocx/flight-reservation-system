package com.omerkoc.payment.dto;

import java.math.BigDecimal;

import com.omerkoc.payment.model.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record PaymentRequestDto(
        @NotNull(message = "Booking ID is required")
        Long bookingId,
        
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}
