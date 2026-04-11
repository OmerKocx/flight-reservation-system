package com.omerkoc.booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BookingRequestDto(
                @NotBlank(message = "Customer ID is required") String customerId,
                Integer flightId) {
}
