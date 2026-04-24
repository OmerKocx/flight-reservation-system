package com.omerkoc.booking.service;

import java.util.List;

import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;

public interface IBookingService {
    BookingResponseDto createBooking(BookingRequestDto request);

    List<BookingResponseDto> getAllBookings();

    BookingResponseDto getBookingById(Integer id);

    BookingResponseDto updateBooking(Integer id, BookingRequestDto request);

    void deleteBooking(Integer id);

    void confirmBooking(Integer id);

    void rejectBooking(Integer id);
}
