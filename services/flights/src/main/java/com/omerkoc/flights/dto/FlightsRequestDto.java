package com.omerkoc.flights.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FlightsRequestDto(
                @NotBlank(message = "Flight code is required") String flightCode,
                @NotBlank(message = "Departure airport is required") String departureAirport,
                @NotBlank(message = "Arrival airport is required") String arrivalAirport,
                @NotNull(message = "Departure time is required") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime departureTime,
                @NotNull(message = "Arrival time is required") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime arrivalTime,
                @NotNull(message = "Status is required") String status,
                @NotNull(message = "Plane id is required") Integer planeId) {

}