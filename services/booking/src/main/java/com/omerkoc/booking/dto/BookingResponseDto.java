package com.omerkoc.booking.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.omerkoc.booking.model.BookingStatus;

import lombok.Builder;

@Builder
public record BookingResponseDto(
        Integer id,
        String bookingCode,
        String customerId,
        Integer flightId,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING) LocalDateTime bookingDate,
        BookingStatus status) {
}
