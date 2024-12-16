package com.jyhun.shop.service;

import com.jyhun.shop.entity.Payment;
import com.jyhun.shop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(Long totalPrice) {

        Payment payment = Payment.builder()
                .price(totalPrice)
                .build();

        return paymentRepository.save(payment);

    }

}
