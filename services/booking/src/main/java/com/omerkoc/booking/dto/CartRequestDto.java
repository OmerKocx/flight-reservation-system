package com.omerkoc.booking.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartRequestDto(
        @NotNull Integer flightId,
        @NotBlank String seatNumber,
        @NotNull @Min(0) BigDecimal price) {
}