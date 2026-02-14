package com.omerkoc.flights.service;

import java.util.List;

import com.omerkoc.flights.dto.PlaneRequestDto;
import com.omerkoc.flights.dto.PlaneResponseDto;

public interface IPlaneService {

    public List<PlaneResponseDto> getAllPlanes();

    public PlaneResponseDto getPlaneById(Integer id);

    public PlaneResponseDto createPlane(PlaneRequestDto plane);

    public PlaneResponseDto updatePlane(Integer id, PlaneRequestDto plane);

    public void deletePlane(Integer id);

    public List<PlaneResponseDto> getPlanesByFlights();
}
