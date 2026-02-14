package com.omerkoc.flights.dto;

public record FlightsResponseDto(
        String id,
        String flightCode,
        String departureAirport,
        String arrivalAirport,
        String departureTime,
        String arrivalTime,
        String status,
        String planeId) {

}
