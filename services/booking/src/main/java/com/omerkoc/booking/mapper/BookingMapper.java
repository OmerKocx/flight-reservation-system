package com.omerkoc.booking.mapper;

import org.springframework.stereotype.Component;

import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.model.Booking;

@Component
public class BookingMapper {

    public Booking toBooking(BookingRequestDto request) {
        return Booking.builder()
                .customerId(request.customerId())
                .flightId(request.flightId())
                .build();
    }

    public BookingResponseDto toResponse(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .customerId(booking.getCustomerId())
                .flightId(booking.getFlightId())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus())
                .build();
    }
}
