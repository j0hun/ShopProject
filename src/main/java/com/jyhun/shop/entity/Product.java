package com.jyhun.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateProduct(String name, String description, String imageUrl, Long price, Category category) {
        if (name != null) this.name = name;
        if (price != null) this.price = price;
        if (description != null) this.description = description;
        if (imageUrl != null) this.imageUrl = imageUrl;
        if (category != null) this.category = category;
    }

}
