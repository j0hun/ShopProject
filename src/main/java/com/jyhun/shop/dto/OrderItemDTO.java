package com.jyhun.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;
    private int quantity;
    private Long price;
    private String status;
    private UserDTO user;
    private ProductDTO product;

}
