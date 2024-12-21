package com.jyhun.shop.dto;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private Long quantity;

}
