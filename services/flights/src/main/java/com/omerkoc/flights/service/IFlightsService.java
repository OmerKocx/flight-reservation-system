package com.omerkoc.flights.service;

import java.util.List;

import com.omerkoc.flights.dto.FlightsResponseDto;
import com.omerkoc.flights.model.Flights;

public interface IFlightsService {

    public List<FlightsResponseDto> getAllFlights();

    public FlightsResponseDto getFlightById(Integer id);

    public FlightsResponseDto createFlight(Flights flight);

    public FlightsResponseDto updateFlight(Integer id, Flights flight);

    public void deleteFlight(Integer id);

    public FlightsResponseDto setPlane(Integer id, Integer planeId);
}
