package com.jyhun.shop.mapper;

import com.jyhun.shop.dto.*;
import com.jyhun.shop.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDTOMapper {

    public UserDTO mapUserToDTOBasic(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getUserRole().name());
        return userDTO;
    }

    public AddressDTO mapAddressToDTOBasic(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setZipcode(address.getZipcode());
        return addressDTO;
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

    public UserDTO mapUserToDTOPlusAddress(User user) {
        UserDTO userDTO = mapUserToDTOBasic(user);
        if(user.getAddress() != null) {
            AddressDTO addressDTO = mapAddressToDTOBasic(user.getAddress());
            userDTO.setAddressDTO(addressDTO);
        }
        return userDTO;
    }

    public OrderItemDTO mapOrderItemToDTOPlusProduct(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = mapOrderItemToDTOBasic(orderItem);
        if(orderItem.getOrder() != null) {
            ProductDTO productDTO = mapProductToDTOBasic(orderItem.getProduct());
            orderItemDTO.setProductDTO(productDTO);
        }
        return orderItemDTO;
    }

    public OrderItemDTO mapOrderItemToDTOPlusProductAndUser(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = mapOrderItemToDTOPlusProduct(orderItem);
        if(orderItem.getUser() != null) {
            UserDTO userDTO = mapUserToDTOPlusAddress(orderItem.getUser());
            orderItemDTO.setUserDTO(userDTO);
        }
        return orderItemDTO;
    }

    public UserDTO mapUserToDTOPlusAddressAndOrderHistory(User user) {
        UserDTO userDTO = mapUserToDTOPlusAddress(user);
        if(user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userDTO.setOrderItemDTOList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDTOPlusProduct)
                    .collect(Collectors.toList()));
        }
        return userDTO;
    }

}
