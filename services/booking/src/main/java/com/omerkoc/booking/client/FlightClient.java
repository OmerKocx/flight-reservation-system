package com.omerkoc.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.omerkoc.booking.dto.FlightResponse;

@FeignClient(name = "flights-service", url = "${application.config.flights-url}")
public interface FlightClient {

    @GetMapping("/{id}")
    FlightResponse getFlightById(@PathVariable("id") Integer id);

    @PostMapping("/set-capacity/{flightId}")
    void decreaseCapacity(@PathVariable("flightId") Integer flightId);
}
