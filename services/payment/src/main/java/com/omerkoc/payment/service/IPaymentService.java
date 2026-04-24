package com.omerkoc.payment.service;

import com.omerkoc.payment.dto.PaymentRequestDto;
import com.omerkoc.payment.dto.PaymentResponseDto;

public interface IPaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto request);
}
