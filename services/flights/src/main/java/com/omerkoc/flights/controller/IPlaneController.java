package com.omerkoc.flights.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.flights.dto.PlaneRequestDto;
import com.omerkoc.flights.dto.PlaneResponseDto;

public interface IPlaneController {

    public ResponseEntity<List<PlaneResponseDto>> getAllPlanes();

    public ResponseEntity<PlaneResponseDto> getPlaneById(Integer id);

    public ResponseEntity<PlaneResponseDto> createPlane(PlaneRequestDto planeRequestDto);

    public ResponseEntity<PlaneResponseDto> updatePlane(Integer id, PlaneRequestDto planeRequestDto);

    public ResponseEntity<Void> deletePlane(Integer id);

    public ResponseEntity<List<PlaneResponseDto>> getPlanesByFlights();
}
