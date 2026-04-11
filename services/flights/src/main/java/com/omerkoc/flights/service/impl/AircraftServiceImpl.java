package com.omerkoc.flights.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omerkoc.flights.dto.AircraftRequestDto;
import com.omerkoc.flights.dto.AircraftResponseDto;
import com.omerkoc.flights.exception.AircraftNotFoundException;
import com.omerkoc.flights.mapper.AircraftMapper;
import com.omerkoc.flights.model.Aircraft;
import com.omerkoc.flights.repository.AircraftRepository;
import com.omerkoc.flights.service.IAircraftService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements IAircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    @Override
    public List<AircraftResponseDto> getAllAircrafts() {
        return aircraftRepository.findAll().stream().map(aircraftMapper::mapToAircraftResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AircraftResponseDto getAircraftById(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("Aircraft ID cannot be null!");
        }
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new AircraftNotFoundException("Aircraft not found with id: " + id));
        return aircraftMapper.mapToAircraftResponseDto(aircraft);
    }

    @Override
    public AircraftResponseDto createAircraft(AircraftRequestDto aircraftRequestDto) {
        return aircraftMapper
                .mapToAircraftResponseDto(aircraftRepository.save(aircraftMapper.mapToAircraft(aircraftRequestDto)));
    }

    @Override
    public AircraftResponseDto updateAircraft(Integer id, AircraftRequestDto aircraftRequestDto) {
        if (id == null || aircraftRequestDto == null) {
            throw new IllegalArgumentException("ID or Request Data cannot be null!");
        }
        Aircraft existingAircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new AircraftNotFoundException("Aircraft not found with id: " + id));

        existingAircraft.setModel(aircraftRequestDto.model());
        existingAircraft.setCapacity(aircraftRequestDto.capacity());
        return aircraftMapper.mapToAircraftResponseDto(aircraftRepository.save(existingAircraft));

    }

    @Override
    public void deleteAircraft(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Aircraft ID cannot be null!");
        }
        aircraftRepository.deleteById(id);
    }

    @Override
    public List<AircraftResponseDto> getAircraftsByFlights() {
        return aircraftRepository.findAll().stream().map(aircraftMapper::mapToAircraftResponseDto)
                .collect(Collectors.toList());
    }
}
