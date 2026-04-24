package com.omerkoc.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.omerkoc.payment.dto.PaymentRequestDto;
import com.omerkoc.payment.dto.PaymentResponseDto;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/payments")
public interface IPaymentController {

    @PostMapping
    ResponseEntity<PaymentResponseDto> processPayment(@RequestBody @Valid PaymentRequestDto request);
}
