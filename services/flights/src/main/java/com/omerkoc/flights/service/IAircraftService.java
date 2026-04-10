package com.omerkoc.flights.service;

import java.util.List;

import com.omerkoc.flights.dto.AircraftRequestDto;
import com.omerkoc.flights.dto.AircraftResponseDto;

public interface IAircraftService {

    public List<AircraftResponseDto> getAllAircrafts();

    public AircraftResponseDto getAircraftById(Integer id);

    public AircraftResponseDto createAircraft(AircraftRequestDto aircraft);

    public AircraftResponseDto updateAircraft(Integer id, AircraftRequestDto aircraft);

    public void deleteAircraft(Integer id);

    public List<AircraftResponseDto> getAircraftsByFlights();
}
