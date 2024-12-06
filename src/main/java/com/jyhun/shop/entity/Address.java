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

    private String city;
    private String street;
    private String zipCode;

    public void updateAddress(String city, String street, String zipCode) {
        if (city != null) this.city = city;
        if (street != null) this.street = street;
        if (zipCode != null) this.zipCode = zipCode;
    }

}


