package com.omerkoc.flights.dto;

import lombok.Builder;

@Builder
public record FlightsResponseDto(
                Integer id,
                String flightCode,
                String departureAirport,
                String arrivalAirport,
                String departureTime,
                String arrivalTime,
                String status,
                Integer planeId) {

}
