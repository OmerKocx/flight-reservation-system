package com.omerkoc.flights.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omerkoc.flights.model.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Integer> {

}
