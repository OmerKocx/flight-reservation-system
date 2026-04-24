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
    @jakarta.transaction.Transactional
    public BookingResponseDto createBooking(BookingRequestDto request) {
        log.info("Creating booking for customer {} on flight {}", request.customerId(), request.flightId());

        checkCustomerAndFlight(request);

        Booking booking = bookingMapper.toBooking(request);
        booking.setBookingCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingDate(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        try {
            log.info("Decreasing capacity for flight: {}", request.flightId());
            flightClient.decreaseCapacity(request.flightId());
        } catch (Exception e) {
            log.error("Kapasite düşürülemedi, işlem geri alınıyor: {}", e.getMessage());
            throw new RuntimeException("Flight capacity could not be updated. Booking cancelled!");
        }

        log.info("Booking created successfully with PNR: {}", savedBooking.getBookingCode());
        return bookingMapper.toResponse(savedBooking);
    }

    private void checkCustomerAndFlight(BookingRequestDto request) {
        try {
            customerClient.getCustomerById(request.customerId());
            flightClient.getFlightById(request.flightId());
        } catch (Exception e) {
            throw new RuntimeException("Customer or Flight service unavailable!");
        }
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto getBookingById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Booking ID cannot be null!");
        }
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        return bookingMapper.toResponse(booking);
    }

    @Override
    public void deleteBooking(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Booking ID cannot be null!");
        }
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingResponseDto updateBooking(Integer id, BookingRequestDto request) {
        throw new UnsupportedOperationException("Unimplemented method 'updateBooking'");
    }

    @Override
    @jakarta.transaction.Transactional
    public void approveBooking(Integer id) {
        log.info("Approving booking: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        booking.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking);
    }

    @Override
    @jakarta.transaction.Transactional
    public void rejectBooking(Integer id) {
        log.info("Rejecting booking: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + id));
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
    }
}
