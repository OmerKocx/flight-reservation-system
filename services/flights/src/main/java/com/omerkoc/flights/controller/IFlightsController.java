package com.omerkoc.flights.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.flights.dto.FlightsResponseDto;
import com.omerkoc.flights.model.Flights;

public interface IFlightsController {

    public ResponseEntity<List<FlightsResponseDto>> getAllFlights();

    public ResponseEntity<FlightsResponseDto> getFlightById(Integer id);

    public ResponseEntity<FlightsResponseDto> createFlight(Flights flight);

    public ResponseEntity<FlightsResponseDto> updateFlight(Integer id, Flights flight);

    public ResponseEntity<Void> deleteFlight(Integer id);

    public ResponseEntity<FlightsResponseDto> setPlane(Integer id, Integer planeId);
}