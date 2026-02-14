package com.omerkoc.flights.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.flights.model.Flights;

public interface IFlightsController {

    public ResponseEntity<List<Flights>> getAllFlights();

    public ResponseEntity<Flights> getFlightById(String id);

    public ResponseEntity<Flights> createFlight(Flights flight);

    public ResponseEntity<Flights> updateFlight(String id, Flights flight);

    public void deleteFlight(String id);
}