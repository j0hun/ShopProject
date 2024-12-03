package com.jyhun.shop.dto;

import com.jyhun.shop.entity.Payment;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long totalPrice;
    private List<OrderItemRequest> items;
    private Payment payment;

}
