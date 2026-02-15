package com.omerkoc.flights.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PlaneRequestDto(
        @NotBlank(message = "Model is required") @NotNull @NotEmpty String model,
        @NotNull(message = "Capacity is required") Integer capacity

) {

}
