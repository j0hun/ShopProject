package com.jyhun.shop.mapper;

import com.jyhun.shop.dto.*;
import com.jyhun.shop.entity.*;
import org.springframework.stereotype.Component;

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

    public CategoryDTO mapCategoryToDTOBasic(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public OrderItemDTO mapOrderItemToDTOBasic(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItemDTO.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setStatus(orderItem.getStatus().name());
        return orderItemDTO;
    }

    public ProductDTO mapProductToDTOBasic(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());
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

    public OrderItemDTO mapOrderItemToDTOPlusProduct(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = mapOrderItemToDTOBasic(orderItem);
        if (orderItem.getOrder() != null) {
            ProductDTO productDTO = mapProductToDTOBasic(orderItem.getProduct());
            orderItemDTO.setProduct(productDTO);
        }
        return orderItemDTO;
    }

    public UserResponseDTO mapUserToDTOPlusAddressAndOrderHistory(User user) {
        UserResponseDTO userResponseDTO = mapUserToDTOPlusAddress(user);
        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userResponseDTO.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDTOPlusProduct)
                    .collect(Collectors.toList()));
        }
        return userResponseDTO;
    }

}
