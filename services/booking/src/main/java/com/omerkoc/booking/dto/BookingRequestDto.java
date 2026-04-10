package com.omerkoc.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookingRequestDto(
        @NotBlank(message = "Customer ID is required") String customerId,
        @NotNull(message = "Flight ID is required") Integer flightId) {
}
