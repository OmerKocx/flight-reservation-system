package com.omerkoc.flights.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FlightsRequestDto(
        @NotBlank(message = "Flight code is required") String flightCode,
        @NotBlank(message = "Departure airport is required") String departureAirport,
        @NotBlank(message = "Arrival airport is required") String arrivalAirport,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime departureTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime arrivalTime,
        String status,
        Integer aircraftId) {

}