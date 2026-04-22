package com.omerkoc.booking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/cart")
public interface ICartController {

    @PostMapping("/{userId}")
    ResponseEntity<CartResponseDto> addToCart(
            @PathVariable String userId,
            @Valid @RequestBody CartRequestDto request);

    @GetMapping("/{userId}")
    ResponseEntity<CartResponseDto> getCart(@PathVariable String userId);

    @DeleteMapping("/{userId}/flight/{flightId}")
    ResponseEntity<CartResponseDto> removeFromCart(
            @PathVariable String userId,
            @PathVariable Integer flightId);

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> clearCart(@PathVariable String userId);

    @PostMapping("/{userId}/checkout")
    ResponseEntity<List<BookingResponseDto>> checkout(@PathVariable String userId);
}