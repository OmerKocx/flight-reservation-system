package com.omerkoc.flights.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.flights.controller.IFlightsController;
import com.omerkoc.flights.model.Flights;

public class FlightsControllerImpl implements IFlightsController {

    @Override
    public ResponseEntity<List<Flights>> getAllFlights() {
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<Flights> getFlightById(String id) {
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<Flights> createFlight(Flights flight) {
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<Flights> updateFlight(String id, Flights flight) {
        return ResponseEntity.ok().body(null);
    }

    @Override
    public void deleteFlight(String id) {

    }

}