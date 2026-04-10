package com.omerkoc.flights.dto;

import lombok.Builder;

@Builder
public record AircraftResponseDto(
                Integer id,
                String model,
                Integer capacity) {

}
