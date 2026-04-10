package com.omerkoc.flights.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omerkoc.flights.model.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {

}
