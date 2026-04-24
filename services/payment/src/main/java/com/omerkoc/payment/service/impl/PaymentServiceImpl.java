package com.omerkoc.payment.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omerkoc.payment.client.BookingClient;
import com.omerkoc.payment.dto.PaymentRequestDto;
import com.omerkoc.payment.dto.PaymentResponseDto;
import com.omerkoc.payment.mapper.PaymentMapper;
import com.omerkoc.payment.model.PaymentStatus;
import com.omerkoc.payment.repository.PaymentRepository;
import com.omerkoc.payment.service.IPaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BookingClient bookingClient;

    @Override
    @Transactional
    public PaymentResponseDto processPayment(PaymentRequestDto request) {
        log.info("Processing payment for booking: {}", request.bookingId());
        
        // 1. Create payment with PENDING status
        var payment = paymentMapper.toPayment(request);
        payment.setStatus(PaymentStatus.PENDING);
        var savedPayment = paymentRepository.save(payment);
        
        // 2. Simulate payment processing logic
        // In a real scenario, this would call a payment gateway
        log.info("Payment processing successful for booking: {}", request.bookingId());
        
        // 3. Update status to SUCCESS
        savedPayment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(savedPayment);
        
        // 4. Notify Booking Service
        try {
            log.info("Notifying booking service to approve booking: {}", request.bookingId());
            bookingClient.approveBooking(request.bookingId().intValue());
        } catch (Exception e) {
            log.error("Failed to notify booking service for booking {}: {}", request.bookingId(), e.getMessage());
            // In a real app, you might want to retry or compensate
        }
        
        return paymentMapper.toResponse(savedPayment);
    }
}
