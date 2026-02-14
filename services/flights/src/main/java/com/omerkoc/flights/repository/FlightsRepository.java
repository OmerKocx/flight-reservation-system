package com.omerkoc.flights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omerkoc.flights.model.Flights;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Integer> {

}