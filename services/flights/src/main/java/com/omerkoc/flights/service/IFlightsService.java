package com.omerkoc.flights.service;

import java.util.List;

import com.omerkoc.flights.dto.FlightsRequestDto;
import com.omerkoc.flights.dto.FlightsResponseDto;

public interface IFlightsService {

    public List<FlightsResponseDto> getAllFlights();

    public FlightsResponseDto getFlightById(Integer id);

    public FlightsResponseDto createFlight(FlightsRequestDto flight);

    public FlightsResponseDto updateFlight(Integer id, FlightsRequestDto flight);

    public void deleteFlight(Integer id);

    public FlightsResponseDto setAircraft(Integer id, Integer aircraftId);

    public FlightsResponseDto setCapacity(Integer flightId);
}
