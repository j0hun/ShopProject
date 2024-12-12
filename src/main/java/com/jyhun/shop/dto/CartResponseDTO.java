package com.jyhun.shop.dto;

import lombok.Data;

@Data
public class CartResponseDTO {

    private Long id;
    private ProductDTO product;
    private int quantity;
    private Long price;

}
