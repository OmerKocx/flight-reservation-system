package com.omerkoc.payment.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/v1/bookings")
public interface BookingClient {

    @PutExchange("/{id}/confirm")
    void confirmBooking(@PathVariable("id") Integer id);

    @PutExchange("/{id}/reject")
    void rejectBooking(@PathVariable("id") Integer id);
}