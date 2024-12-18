package com.jyhun.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baseAddress;
    private String detailAddress;
    private String postalCode;

    public void updateAddress(String baseAddress, String detailAddress, String postalCode) {
        if (baseAddress != null) this.baseAddress = baseAddress;
        if (detailAddress != null) this.detailAddress = detailAddress;
        if (postalCode != null) this.postalCode = postalCode;
    }

}


