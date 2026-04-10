package com.omerkoc.flights.mapper;

import org.springframework.stereotype.Component;

import com.omerkoc.flights.dto.FlightsRequestDto;
import com.omerkoc.flights.dto.FlightsResponseDto;
import com.omerkoc.flights.model.Flights;

@Component
public class FlightsMapper {

    public FlightsResponseDto mapToFlightsResponseDto(Flights flights) {
        return FlightsResponseDto.builder()
                .id(flights.getId())
                .flightCode(flights.getFlightCode())
                .departureAirport(flights.getDepartureAirport())
                .arrivalAirport(flights.getArrivalAirport())
                .departureTime(flights.getDepartureTime())
                .arrivalTime(flights.getArrivalTime())
                .status(flights.getStatus())
                .aircraftId(flights.getAircraft().getId())
                .capacity(flights.getCapacity())
                .build();
    }

    public Flights mapToFlights(FlightsRequestDto flightsRequestDto) {
        return Flights.builder()
                .flightCode(flightsRequestDto.flightCode())
                .departureAirport(flightsRequestDto.departureAirport())
                .arrivalAirport(flightsRequestDto.arrivalAirport())
                .departureTime(flightsRequestDto.departureTime())
                .arrivalTime(flightsRequestDto.arrivalTime())
                .status(flightsRequestDto.status())
                .build();
    }
}
