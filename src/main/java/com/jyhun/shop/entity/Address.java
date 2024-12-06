package com.jyhun.shop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String zipCode;

    @Builder
    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public void updateAddress(String city, String street, String zipCode) {
        if (city != null) this.city = city;
        if (street != null) this.street = street;
        if (zipCode != null) this.zipCode = zipCode;
    }

}


