package com.jyhun.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {

    private String baseAddress;
    private String detailAddress;
    private String postalCode;

}
