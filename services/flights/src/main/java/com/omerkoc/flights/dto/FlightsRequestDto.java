package com.omerkoc.flights.dto;

import jakarta.validation.constraints.NotBlank;

public record FlightsRequestDto(
        @NotBlank(message = "Flight code is required") String flightCode,
        @NotBlank(message = "Departure airport is required") String departureAirport,
        @NotBlank(message = "Arrival airport is required") String arrivalAirport,
        @NotBlank(message = "Departure time is required") String departureTime,
        @NotBlank(message = "Arrival time is required") String arrivalTime,
        @NotBlank(message = "Status is required") String status,
        @NotBlank(message = "Plane ID is required") String planeId) {

}