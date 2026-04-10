package com.omerkoc.flights.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.flights.dto.FlightsRequestDto;
import com.omerkoc.flights.dto.FlightsResponseDto;

public interface IFlightsController {

    public ResponseEntity<List<FlightsResponseDto>> getAllFlights();

    public ResponseEntity<FlightsResponseDto> getFlightById(Integer id);

    public ResponseEntity<FlightsResponseDto> createFlight(FlightsRequestDto flight);

    public ResponseEntity<FlightsResponseDto> updateFlight(Integer id, FlightsRequestDto flight);

    public ResponseEntity<Void> deleteFlight(Integer id);

    public ResponseEntity<FlightsResponseDto> setAircraft(Integer id, Integer aircraftId);

    public ResponseEntity<FlightsResponseDto> setCapacity(Integer flightId);
}