package com.omerkoc.flights.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AircraftRequestDto(
        @NotBlank(message = "Model is required") @NotEmpty String model,
        Integer capacity

) {

}
