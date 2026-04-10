package com.omerkoc.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omerkoc.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
