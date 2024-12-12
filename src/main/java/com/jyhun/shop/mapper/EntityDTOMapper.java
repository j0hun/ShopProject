package com.jyhun.shop.mapper;

import com.jyhun.shop.dto.*;
import com.jyhun.shop.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDTOMapper {

    public UserResponseDTO mapUserToDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole().name());
        userResponseDTO.setName(user.getName());
        return userResponseDTO;
    }

    public AddressResponseDTO mapAddressToDTO(Address address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .build();
    }

    public CategoryResponseDTO mapCategoryToDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        return categoryResponseDTO;
    }

    public OrderItemResponseDTO mapOrderItemToDTO(OrderItem orderItem) {
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setId(orderItem.getId());
        orderItemResponseDTO.setQuantity(orderItem.getQuantity());
        orderItemResponseDTO.setPrice(orderItem.getPrice());
        orderItemResponseDTO.setStatus(orderItem.getStatus().name());
        orderItemResponseDTO.setProduct(mapProductToDTO(orderItem.getProduct()));
        return orderItemResponseDTO;
    }

    public ProductDTO mapProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setCategory(mapCategoryToDTO(product.getCategory()));
        return productDTO;
    }

    public UserResponseDTO mapUserToDTOPlusAddress(User user) {
        UserResponseDTO userResponseDTO = mapUserToDTO(user);
        if (user.getAddress() != null) {
            AddressResponseDTO addressResponseDTO = mapAddressToDTO(user.getAddress());
            userResponseDTO.setAddress(addressResponseDTO);
        }
        return userResponseDTO;
    }

    public OrderItemResponseDTO mapOrderItemToDTOPlusProduct(OrderItem orderItem) {
        OrderItemResponseDTO orderItemResponseDTO = mapOrderItemToDTO(orderItem);
        if (orderItem.getOrder() != null) {
            ProductDTO productDTO = mapProductToDTO(orderItem.getProduct());
            orderItemResponseDTO.setProduct(productDTO);
        }
        return orderItemResponseDTO;
    }

    public OrderItemResponseDTO mapOrderItemToDTOPlusOrderItem(OrderItem orderItem) {
        OrderItemResponseDTO orderItemResponseDTO = mapOrderItemToDTO(orderItem);
        if (orderItem.getOrder() != null) {
            ProductDTO productDTO = mapProductToDTO(orderItem.getProduct());
            orderItemResponseDTO.setProduct(productDTO);
        }
        return orderItemResponseDTO;
    }

    private OrderResponseDTO mapOrderToDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        orderResponseDTO.setOrderItemList(order.getOrderItemList()
                .stream()
                .map(this::mapOrderItemToDTO)
                .collect(Collectors.toList()));
        return orderResponseDTO;
    }

    public UserResponseDTO mapUserToDTOPlusAddressAndOrderHistory(User user) {
        UserResponseDTO userResponseDTO = mapUserToDTOPlusAddress(user);
        if (user.getOrderList() != null && !user.getOrderList().isEmpty()) {
            userResponseDTO.setOrderList(user.getOrderList()
                    .stream()
                    .map(this::mapOrderToDTO)
                    .collect(Collectors.toList()));
        }
        return userResponseDTO;
    }

    public CartResponseDTO mapCartToDTO(Cart cart) {
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setId(cart.getId());
        cartResponseDTO.setProduct(mapProductToDTO(cart.getProduct()));
        cartResponseDTO.setQuantity(cart.getQuantity());
        cartResponseDTO.setPrice(cart.getPrice());
        return cartResponseDTO;
    }

    public List<CartResponseDTO> mapCartListToDTO(List<Cart> cartList) {
        return cartList.stream().map(this::mapCartToDTO)
                .collect(Collectors.toList());
    }

}
