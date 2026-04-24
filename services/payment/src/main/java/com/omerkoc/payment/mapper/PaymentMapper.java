package com.omerkoc.payment.mapper;

import org.springframework.stereotype.Component;

import com.omerkoc.payment.dto.PaymentRequestDto;
import com.omerkoc.payment.dto.PaymentResponseDto;
import com.omerkoc.payment.model.Payment;
import com.omerkoc.payment.model.PaymentStatus;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentRequestDto request) {
        return Payment.builder()
                .bookingId(request.bookingId())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .status(PaymentStatus.SUCCESS) // Default for skeleton
                .build();
    }

    public PaymentResponseDto toResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
