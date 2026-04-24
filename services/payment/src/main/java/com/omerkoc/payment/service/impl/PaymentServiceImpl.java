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
import com.omerkoc.payment.exception.PaymentProcessingException;

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
        
        var payment = paymentMapper.toPayment(request);
        payment.setStatus(PaymentStatus.PENDING);
        var savedPayment = paymentRepository.save(payment);
        
        try {
            boolean isSuccessful = simulatePayment();
            
            if (isSuccessful) {
                savedPayment.setStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(savedPayment);
                
                log.info("Payment successful, notifying booking service: {}", request.bookingId());
                bookingClient.confirmBooking(request.bookingId().intValue());
            } else {
                throw new PaymentProcessingException("Payment declined by gateway");
            }
        } catch (Exception e) {
            log.error("Payment failed for booking {}: {}", request.bookingId(), e.getMessage());
            savedPayment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(savedPayment);
            
            try {
                bookingClient.rejectBooking(request.bookingId().intValue());
            } catch (Exception be) {
                log.error("Failed to notify booking service of rejection: {}", be.getMessage());
            }
            
            if (e instanceof PaymentProcessingException) {
                throw (PaymentProcessingException) e;
            }
            throw new PaymentProcessingException("Unexpected error during payment: " + e.getMessage());
        }
        
        return paymentMapper.toResponse(savedPayment);
    }

    private boolean simulatePayment() {
        return Math.random() > 0.1;
    }
}
