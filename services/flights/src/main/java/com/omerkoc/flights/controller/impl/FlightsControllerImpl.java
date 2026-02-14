package com.omerkoc.flights.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omerkoc.flights.controller.IFlightsController;
import com.omerkoc.flights.dto.FlightsResponseDto;
import com.omerkoc.flights.model.Flights;
import com.omerkoc.flights.service.IFlightsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightsControllerImpl implements IFlightsController {

    private final IFlightsService flightsService;

    @Override
    @GetMapping
    public ResponseEntity<List<FlightsResponseDto>> getAllFlights() {
        return ResponseEntity.ok().body(flightsService.getAllFlights());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FlightsResponseDto> getFlightById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(flightsService.getFlightById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<FlightsResponseDto> createFlight(@RequestBody Flights flight) {
        return ResponseEntity.ok().body(flightsService.createFlight(flight));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FlightsResponseDto> updateFlight(@PathVariable Integer id, @RequestBody Flights flight) {
        return ResponseEntity.ok().body(flightsService.updateFlight(id, flight));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Integer id) {
        flightsService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/{id}/plane/{planeId}")
    public ResponseEntity<FlightsResponseDto> setPlane(@PathVariable Integer id, @PathVariable Integer planeId) {
        return ResponseEntity.ok().body(flightsService.setPlane(id, planeId));
    }
}