package com.omerkoc.flights.mapper;

import org.springframework.stereotype.Component;

import com.omerkoc.flights.dto.AircraftRequestDto;
import com.omerkoc.flights.dto.AircraftResponseDto;
import com.omerkoc.flights.model.Aircraft;

@Component
public class AircraftMapper {

    public Aircraft mapToAircraft(AircraftRequestDto aircraftRequestDto) {
        return Aircraft.builder()
                .model(aircraftRequestDto.model())
                .capacity(aircraftRequestDto.capacity())
                .build();
    }

    public AircraftResponseDto mapToAircraftResponseDto(Aircraft aircraft) {
        return AircraftResponseDto.builder()
                .id(aircraft.getId())
                .model(aircraft.getModel())
                .capacity(aircraft.getCapacity())
                .build();
    }
}
