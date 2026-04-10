package com.omerkoc.flights.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.flights.dto.AircraftRequestDto;
import com.omerkoc.flights.dto.AircraftResponseDto;

public interface IAircraftController {

    public ResponseEntity<List<AircraftResponseDto>> getAllAircrafts();

    public ResponseEntity<AircraftResponseDto> getAircraftById(Integer id);

    public ResponseEntity<AircraftResponseDto> createAircraft(AircraftRequestDto aircraftRequestDto);

    public ResponseEntity<AircraftResponseDto> updateAircraft(Integer id, AircraftRequestDto aircraftRequestDto);

    public ResponseEntity<Void> deleteAircraft(Integer id);

    public ResponseEntity<List<AircraftResponseDto>> getAircraftsByFlights();
}
