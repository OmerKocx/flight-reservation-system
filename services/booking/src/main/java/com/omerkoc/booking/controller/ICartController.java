package com.omerkoc.booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/cart")
public interface ICartController {

    // URL: POST /api/v1/cart/{userId}
    @PostMapping("/{userId}")
    ResponseEntity<CartResponseDto> addToCart(
            @PathVariable String userId,
            @Valid @RequestBody CartRequestDto request);

    // URL: GET /api/v1/cart/{userId}
    @GetMapping("/{userId}")
    ResponseEntity<CartResponseDto> getCart(@PathVariable String userId);

    // URL: DELETE /api/v1/cart/{userId}
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> clearCart(@PathVariable String userId);

    // URL: POST /api/v1/cart/{userId}/checkout
    @PostMapping("/{userId}/checkout")
    ResponseEntity<Object> checkout(@PathVariable String userId);
}