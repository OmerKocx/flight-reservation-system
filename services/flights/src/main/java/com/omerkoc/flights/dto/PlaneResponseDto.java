package com.omerkoc.flights.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record PlaneResponseDto(
        Integer id,
        String model,
        Integer capacity,
        List<FlightsResponseDto> flights) {

}
