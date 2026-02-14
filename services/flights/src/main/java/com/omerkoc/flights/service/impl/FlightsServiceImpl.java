package com.omerkoc.flights.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omerkoc.flights.dto.FlightsResponseDto;
import com.omerkoc.flights.mapper.FlightsMapper;
import com.omerkoc.flights.model.Flights;
import com.omerkoc.flights.model.Plane;
import com.omerkoc.flights.repository.FlightsRepository;
import com.omerkoc.flights.repository.PlaneRepository;
import com.omerkoc.flights.service.IFlightsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightsServiceImpl implements IFlightsService {

    private final FlightsRepository flightsRepository;
    private final FlightsMapper flightsMapper;
    private final PlaneRepository planeRepository;

    @Override
    public List<FlightsResponseDto> getAllFlights() {
        return flightsRepository.findAll().stream().map(flightsMapper::mapToFlightsResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightsResponseDto getFlightById(Integer id) {
        return flightsMapper.mapToFlightsResponseDto(
                flightsRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id)));
    }

    @Override
    public FlightsResponseDto createFlight(Flights flight) {
        return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(flight));
    }

    @Override
    public FlightsResponseDto updateFlight(Integer id, Flights flight) {

        Flights existingFlight = flightsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        existingFlight.setFlightCode(flight.getFlightCode());
        existingFlight.setDepartureAirport(flight.getDepartureAirport());
        existingFlight.setArrivalAirport(flight.getArrivalAirport());
        existingFlight.setDepartureTime(flight.getDepartureTime());
        existingFlight.setArrivalTime(flight.getArrivalTime());
        existingFlight.setStatus(flight.getStatus());
        return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(existingFlight));
    }

    @Override
    public void deleteFlight(Integer id) {
        flightsRepository.deleteById(id);
    }

    @Override
    public FlightsResponseDto setPlane(Integer id, Integer planeId) {
        Flights existingFlight = flightsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        Plane plane = planeRepository.findById(planeId)
                .orElseThrow(() -> new RuntimeException("Plane not found with id: " + planeId));
        existingFlight.setPlane(plane);
        return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(existingFlight));
    }

}