package com.jyhun.shop.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {

    private Long price;
    private Long orderId;

}
