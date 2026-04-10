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

import com.omerkoc.flights.controller.IAircraftController;
import com.omerkoc.flights.dto.AircraftRequestDto;
import com.omerkoc.flights.dto.AircraftResponseDto;
import com.omerkoc.flights.service.IAircraftService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/aircrafts")
@RequiredArgsConstructor
public class AircraftControllerImpl implements IAircraftController {

    private final IAircraftService aircraftService;

    @Override
    @GetMapping
    public ResponseEntity<List<AircraftResponseDto>> getAllAircrafts() {
        return ResponseEntity.ok().body(aircraftService.getAllAircrafts());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponseDto> getAircraftById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(aircraftService.getAircraftById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<AircraftResponseDto> createAircraft(@Valid @RequestBody AircraftRequestDto aircraftRequestDto) {
        return ResponseEntity.ok().body(aircraftService.createAircraft(aircraftRequestDto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponseDto> updateAircraft(@PathVariable Integer id,
            @Valid @RequestBody AircraftRequestDto aircraftRequestDto) {
        return ResponseEntity.ok().body(aircraftService.updateAircraft(id, aircraftRequestDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Integer id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/flights")
    public ResponseEntity<List<AircraftResponseDto>> getAircraftsByFlights() {
        return ResponseEntity.ok().body(aircraftService.getAircraftsByFlights());
    }
}
