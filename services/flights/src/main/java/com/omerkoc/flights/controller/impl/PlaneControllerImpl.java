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

import com.omerkoc.flights.controller.IPlaneController;
import com.omerkoc.flights.dto.PlaneRequestDto;
import com.omerkoc.flights.dto.PlaneResponseDto;
import com.omerkoc.flights.service.IPlaneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/planes")
@RequiredArgsConstructor
public class PlaneControllerImpl implements IPlaneController {

    private final IPlaneService planeService;

    @Override
    @GetMapping
    public ResponseEntity<List<PlaneResponseDto>> getAllPlanes() {
        return ResponseEntity.ok().body(planeService.getAllPlanes());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PlaneResponseDto> getPlaneById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(planeService.getPlaneById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<PlaneResponseDto> createPlane(@Valid @RequestBody PlaneRequestDto planeRequestDto) {
        return ResponseEntity.ok().body(planeService.createPlane(planeRequestDto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PlaneResponseDto> updatePlane(@PathVariable Integer id,
            @Valid @RequestBody PlaneRequestDto planeRequestDto) {
        return ResponseEntity.ok().body(planeService.updatePlane(id, planeRequestDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlane(@PathVariable Integer id) {
        planeService.deletePlane(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/flights")
    public ResponseEntity<List<PlaneResponseDto>> getPlanesByFlights() {
        return ResponseEntity.ok().body(planeService.getPlanesByFlights());
    }
}
