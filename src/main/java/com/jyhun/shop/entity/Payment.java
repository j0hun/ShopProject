package com.jyhun.shop.entity;

import com.jyhun.shop.enums.OrderStatus;
import com.jyhun.shop.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Builder.Default
    private Long refundedAmount = 0L;

    public void cancel() {
        this.paymentStatus = PaymentStatus.CANCELED;
    }

    public void addRefundAmount(Long refundAmount) {
        this.refundedAmount += refundAmount;
    }

}
