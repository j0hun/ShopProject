package com.jyhun.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {

    private Long id;
    private Long quantity;
    private Long price;
    private String status;
    private ProductDTO product;

}
