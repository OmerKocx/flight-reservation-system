package com.omerkoc.booking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omerkoc.booking.client.CustomerClient;
import com.omerkoc.booking.client.FlightClient;
import com.omerkoc.booking.dto.BookingRequestDto;
import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.exception.BookingNotFoundException;
import com.omerkoc.booking.mapper.BookingMapper;
import com.omerkoc.booking.model.Booking;
import com.omerkoc.booking.model.BookingStatus;
import com.omerkoc.booking.repository.BookingRepository;
import com.omerkoc.booking.service.IBookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CustomerClient customerClient;
    private final FlightClient flightClient;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto request) {
        log.info("Creating booking for customer {} on flight {}", request.customerId(), request.flightId());

        try {
            var customer = customerClient.getCustomerById(request.customerId());
            if (customer == null)
                throw new BookingNotFoundException("Customer not found");
        } catch (Exception e) {
            throw new BookingNotFoundException(
                    "Customer service communication error or not found: " + request.customerId());
        }

        try {
            var flight = flightClient.getFlightById(request.flightId());
            if (flight == null)
                throw new BookingNotFoundException("Flight not found");
        } catch (Exception e) {
            throw new BookingNotFoundException(
                    "Flight service communication error or not found: " + request.flightId());
        }

        Booking booking = bookingMapper.toBooking(request);

        booking.setBookingCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setStatus(BookingStatus.BOOKED);
        booking.setBookingDate(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully with PNR: {}", savedBooking.getBookingCode());

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        return bookingMapper.toResponse(booking);
    }

    @Override
    public void deleteBooking(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingResponseDto updateBooking(Integer id, BookingRequestDto request) {
        throw new UnsupportedOperationException("Unimplemented method 'updateBooking'");
    }
}
