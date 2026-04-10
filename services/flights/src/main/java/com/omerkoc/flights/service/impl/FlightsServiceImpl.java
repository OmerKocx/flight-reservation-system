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
                return flightsRepository.findAll().stream().map(flightsMapper::mapToFlightsResponseDto)
                                .collect(Collectors.toList());
        }

        @Override
        public FlightsResponseDto getFlightById(Integer id) {
                return flightsMapper.mapToFlightsResponseDto(
                                flightsRepository.findById(id)
                                                .orElseThrow(() -> new FlightsNotFoundException(
                                                                "Flight not found with id: " + id)));
        }

        @Override
        public FlightsResponseDto createFlight(FlightsRequestDto flightDto) {
                Aircraft aircraft = aircraftRepository.findById(flightDto.aircraftId())
                                .orElseThrow(() -> new AircraftNotFoundException(
                                                "Aircraft not found with id: " + flightDto.aircraftId()));

                Flights flight = flightsMapper.mapToFlights(flightDto);
                flight.setAircraft(aircraft);
                flight.setCapacity(aircraft.getCapacity());
                return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(flight));
        }

        @Override
        public FlightsResponseDto updateFlight(Integer id, FlightsRequestDto flightDto) {

                Flights existingFlight = flightsRepository.findById(id)
                                .orElseThrow(() -> new FlightsNotFoundException(
                                                "Flight not found with id: " + id));

                Aircraft aircraft = aircraftRepository.findById(flightDto.aircraftId())
                                .orElseThrow(() -> new AircraftNotFoundException(
                                                "Aircraft not found with id: " + flightDto.aircraftId()));

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
                flightsRepository.deleteById(id);
        }

        @Override
        public FlightsResponseDto setAircraft(Integer id, Integer aircraftId) {
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
                Flights existingFlight = flightsRepository.findById(flightId)
                                .orElseThrow(() -> new FlightsNotFoundException(
                                                "Flight not found with id: " + flightId));

                if (existingFlight.getCapacity() <= 0) {
                        throw new IllegalStateException("Uçak doldu reis, daha fazla bilet kesemezsin!");
                }

                existingFlight.setCapacity(existingFlight.getCapacity() - 1);
                return flightsMapper.mapToFlightsResponseDto(flightsRepository.save(existingFlight));
        }
}