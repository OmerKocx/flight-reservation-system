package com.omerkoc.booking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;

public interface IBookingController {
    ResponseEntity<BookingResponseDto> createBooking(BookingRequestDto request);

    ResponseEntity<List<BookingResponseDto>> getAllBookings();

    ResponseEntity<BookingResponseDto> getBookingById(Integer id);

    ResponseEntity<BookingResponseDto> updateBooking(Integer id, BookingRequestDto request);

    ResponseEntity<Void> deleteBooking(Integer id);

    ResponseEntity<Void> approveBooking(Integer id);

    ResponseEntity<Void> rejectBooking(Integer id);
}
