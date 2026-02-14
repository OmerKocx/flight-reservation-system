package com.omerkoc.flights.mapper;

import org.springframework.stereotype.Service;

import com.omerkoc.flights.dto.PlaneRequestDto;
import com.omerkoc.flights.dto.PlaneResponseDto;
import com.omerkoc.flights.model.Plane;

@Service
public class PlaneMapper {

    public Plane mapToPlane(PlaneRequestDto planeRequestDto) {
        return Plane.builder()
                .model(planeRequestDto.model())
                .capacity(planeRequestDto.capacity())
                .build();
    }

    public PlaneResponseDto mapToPlaneResponseDto(Plane plane) {
        return PlaneResponseDto.builder()
                .id(plane.getId())
                .model(plane.getModel())
                .capacity(plane.getCapacity())
                .build();
    }
}
