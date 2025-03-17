package com.jyhun.shop.service;

import com.jyhun.shop.entity.Payment;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.enums.PaymentStatus;
import com.jyhun.shop.exception.InsufficientBalanceException;
import com.jyhun.shop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(User user, Long totalPrice) {

        if (user.getBalance() < totalPrice) {
            log.error("결제 실패 - 잔액 부족. 사용자 잔액: {}, 결제 금액: {}", user.getBalance(), totalPrice);
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }

        user.updateBalance(user.getBalance() - totalPrice);

        Payment payment = Payment.builder()
                .price(totalPrice)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        return paymentRepository.save(payment);

    }

}
