package com.jyhun.shop.entity;

import com.jyhun.shop.exception.InsufficientStockException;
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

    private Long stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateProduct(String name, String description, String imageUrl, Long price, Long stock, Category category) {
        if (name != null) this.name = name;
        if (price != null) this.price = price;
        if (stock != null) this.stock = stock;
        if (description != null) this.description = description;
        if (imageUrl != null) this.imageUrl = imageUrl;
        if (category != null) this.category = category;
    }

    public void decreaseStock(Long quantity) {
        if (this.stock < quantity) {
            throw new InsufficientStockException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }


    public void increaseStock(Long quantity) {
        this.stock += quantity;
    }
}
