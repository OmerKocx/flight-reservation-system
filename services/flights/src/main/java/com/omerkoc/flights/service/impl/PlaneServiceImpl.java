package com.omerkoc.flights.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omerkoc.flights.dto.PlaneRequestDto;
import com.omerkoc.flights.dto.PlaneResponseDto;
import com.omerkoc.flights.mapper.PlaneMapper;
import com.omerkoc.flights.model.Plane;
import com.omerkoc.flights.repository.PlaneRepository;
import com.omerkoc.flights.service.IPlaneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements IPlaneService {

    private final PlaneRepository planeRepository;
    private final PlaneMapper planeMapper;

    @Override
    public List<PlaneResponseDto> getAllPlanes() {
        return planeRepository.findAll().stream().map(planeMapper::mapToPlaneResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlaneResponseDto getPlaneById(Integer id) {

        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plane not found with id: " + id));
        return planeMapper.mapToPlaneResponseDto(plane);
    }

    @Override
    public PlaneResponseDto createPlane(PlaneRequestDto planeRequestDto) {
        return planeMapper.mapToPlaneResponseDto(planeRepository.save(planeMapper.mapToPlane(planeRequestDto)));
    }

    @Override
    public PlaneResponseDto updatePlane(Integer id, PlaneRequestDto planeRequestDto) {
        Plane existingPlane = planeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plane not found with id: " + id));

        existingPlane.setModel(planeRequestDto.model());
        existingPlane.setCapacity(planeRequestDto.capacity());
        return planeMapper.mapToPlaneResponseDto(planeRepository.save(existingPlane));

    }

    @Override
    public void deletePlane(Integer id) {
        planeRepository.deleteById(id);
    }

    @Override
    public List<PlaneResponseDto> getPlanesByFlights() {
        return planeRepository.findAll().stream().map(planeMapper::mapToPlaneResponseDto)
                .collect(Collectors.toList());
    }
}
