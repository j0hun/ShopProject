package com.jyhun.shop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Response {

    private int status;
    private String message;

    private String token;
    private String role;
    private String expirationTime;

    private int totalPage;
    private long totalElement;

    private AddressRequestDTO address;

    private UserResponseDTO user;
    private List<UserResponseDTO> userList;

    private CategoryDTO category;
    private List<CategoryDTO> categoryList;

    private ProductDTO product;
    private List<ProductDTO> productList;

    private OrderItemDTO orderItem;
    private List<OrderItemDTO> orderItemList;

    private OrderDTO order;
    private List<OrderDTO> orderList;
}
