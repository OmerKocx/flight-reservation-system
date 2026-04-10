package com.omerkoc.booking.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record FlightResponse(
                Integer id,
                String flightCode,
                String departureAirport,
                String arrivalAirport,
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime departureTime,
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime arrivalTime,
                String status,
                Integer aircraftId) {
}
