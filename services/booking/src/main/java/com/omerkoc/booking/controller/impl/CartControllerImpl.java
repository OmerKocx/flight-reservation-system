package com.omerkoc.booking.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omerkoc.booking.controller.ICartController;
import com.omerkoc.booking.dto.BookingResponseDto;
import com.omerkoc.booking.dto.CartRequestDto;
import com.omerkoc.booking.dto.CartResponseDto;
import com.omerkoc.booking.service.ICartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartControllerImpl implements ICartController {

    private final ICartService cartService;

    @PostMapping("/{userId}")
    @Override
    public ResponseEntity<CartResponseDto> addToCart(
            @PathVariable String userId,
            @RequestBody CartRequestDto request) {

        return ResponseEntity.ok(cartService.addToCart(userId, request));
    }

    @GetMapping("/{userId}")
    @Override
    public ResponseEntity<CartResponseDto> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping("/{userId}/flight/{flightId}")
    @Override
    public ResponseEntity<CartResponseDto> removeFromCart(
            @PathVariable String userId,
            @PathVariable Integer flightId) {
        CartResponseDto response = cartService.removeFromCart(userId, flightId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/checkout")
    @Override
    public ResponseEntity<List<BookingResponseDto>> checkout(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.checkout(userId));
    }
}