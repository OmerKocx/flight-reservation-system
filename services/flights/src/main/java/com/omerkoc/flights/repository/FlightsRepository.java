package com.omerkoc.flights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omerkoc.flights.model.Flights;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Integer> {

    @Modifying
    @Query("UPDATE Flights f SET f.capacity = f.capacity - 1 WHERE f.id = :id AND f.capacity > 0")
    int decreaseCapacitySafely(@Param("id") Integer id);
}