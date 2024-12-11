package com.jyhun.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    private List<OrderItemRequest> items;

}
