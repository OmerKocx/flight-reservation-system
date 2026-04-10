package com.omerkoc.flights.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omerkoc.flights.dto.FlightsRequestDto;
import com.omerkoc.flights.dto.FlightsResponseDto;
import com.omerkoc.flights.exception.AircraftNotFoundException;
import com.omerkoc.flights.exception.FlightsNotFoundException;
import com.omerkoc.flights.mapper.FlightsMapper;
import com.omerkoc.flights.model.Aircraft;
import com.omerkoc.flights.model.Flights;
import com.omerkoc.flights.repository.AircraftRepository;
import com.omerkoc.flights.repository.FlightsRepository;
import com.omerkoc.flights.service.IFlightsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightsServiceImpl implements IFlightsService {

        private final FlightsRepository flightsRepository;
        private final FlightsMapper flightsMapper;
        private final AircraftRepository aircraftRepository;

        @Override
        public List<FlightsResponseDto> getAllFlights() {
                return flightsRepository.findAll().stream()
                                .map(flightsMapper::mapToFlightsResponseDto)
                                .collect(Collectors.toList());
        }

        @Override
        public FlightsResponseDto getFlightById(Integer id) {
                // ID kontrolü
                if (id == null) {
                        throw new IllegalArgumentException("Flight ID cannot be null!");
                }

                return flightsMapper.mapToFlightsResponseDto(
                                flightsRepository.findById(id)
                                                .orElseThrow(() -> new FlightsNotFoundException(
                                                                "Flight not found with id: " + id)));
        }

        @Transactional
        @Override
        public FlightsResponseDto createFlight(FlightsRequestDto flightDto) {
                // 1. DTO Null kontrolü
                if (flightDto == null) {
                        throw new IllegalArgumentException("Flight request data cannot be null!");
                }

                // 2. Aircraft ID Null kontrolü
                if (flightDto.aircraftId() == null) {
                        throw new IllegalArgumentException("Aircraft ID cannot be null!");
                }

                Aircraft aircraft = aircraftRepository.findById(flightDto.aircraftId())
                                .orElseThrow(() -> new AircraftNotFoundException(
                                                "Aircraft not found with id: " + flightDto.aircraftId()));

                Flights flight = flightsMapper.mapToFlights(flightDto);

                flight.setAircraft(aircraft);
                flight.setCapacity(aircraft.getCapacity());

                return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(flight));
        }

        @Transactional
        @Override
        public FlightsResponseDto updateFlight(Integer id, FlightsRequestDto flightDto) {
                if (id == null || flightDto == null) {
                        throw new IllegalArgumentException("ID or Request Data cannot be null!");
                }

                Flights existingFlight = flightsRepository.findById(id)
                                .orElseThrow(() -> new FlightsNotFoundException(
                                                "Flight not found with id: " + id));

                Aircraft aircraft = aircraftRepository.findById(flightDto.aircraftId())
                                .orElseThrow(() -> new AircraftNotFoundException(
                                                "Aircraft not found with id: " + flightDto.aircraftId()));

                // Null-safe güncellemeler
                existingFlight.setFlightCode(flightDto.flightCode());
                existingFlight.setDepartureAirport(flightDto.departureAirport());
                existingFlight.setArrivalAirport(flightDto.arrivalAirport());
                existingFlight.setDepartureTime(flightDto.departureTime());
                existingFlight.setArrivalTime(flightDto.arrivalTime());
                existingFlight.setStatus(flightDto.status());
                existingFlight.setAircraft(aircraft);

                return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(existingFlight));
        }

        @Override
        public void deleteFlight(Integer id) {
                if (id == null) {
                        throw new IllegalArgumentException("Flight ID cannot be null!");
                }
                if (!flightsRepository.existsById(id)) {
                        throw new FlightsNotFoundException("Flight not found with id: " + id);
                }
                flightsRepository.deleteById(id);
        }

        @Transactional
        @Override
        public FlightsResponseDto setAircraft(Integer id, Integer aircraftId) {
                if (id == null || aircraftId == null) {
                        throw new IllegalArgumentException("IDs cannot be null!");
                }

                Flights existingFlight = flightsRepository.findById(id)
                                .orElseThrow(() -> new FlightsNotFoundException(
                                                "Flight not found with id: " + id));

                Aircraft aircraft = aircraftRepository.findById(aircraftId)
                                .orElseThrow(() -> new AircraftNotFoundException(
                                                "Aircraft not found with id: " + aircraftId));

                existingFlight.setAircraft(aircraft);
                return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(existingFlight));
        }

        @Transactional
        @Override
        public FlightsResponseDto setCapacity(Integer flightId) {
                if (flightId == null) {
                        throw new IllegalArgumentException("Flight ID cannot be null!");
                }

                Flights existingFlight = flightsRepository.findById(flightId)
                                .orElseThrow(() -> new FlightsNotFoundException(
                                                "Flight not found with id: " + flightId));

                // Capacity null kontrolü (Veritabanında kaza olmasın)
                Integer currentCapacity = existingFlight.getCapacity();
                if (currentCapacity == null || currentCapacity <= 0) {
                        throw new IllegalStateException("Uçak doldu veya kapasite bilgisi geçersiz!");
                }

                existingFlight.setCapacity(currentCapacity - 1);
                return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(existingFlight));
        }
}