package com.omerkoc.payment.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.omerkoc.payment.controller.IPaymentController;
import com.omerkoc.payment.dto.PaymentRequestDto;
import com.omerkoc.payment.dto.PaymentResponseDto;
import com.omerkoc.payment.service.IPaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentControllerImpl implements IPaymentController {

    private final IPaymentService paymentService;

    @Override
    public ResponseEntity<PaymentResponseDto> processPayment(PaymentRequestDto request) {
        log.info("Processing payment for booking: {}", request.bookingId());
        return ResponseEntity.ok(paymentService.processPayment(request));
    }
}
