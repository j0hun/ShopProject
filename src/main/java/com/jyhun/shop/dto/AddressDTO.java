package com.jyhun.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String city;
    private String street;
    private String zipcode;
    private UserDTO userDTO;

}
