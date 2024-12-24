package com.jyhun.shop.repository;

import com.jyhun.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByProductIdAndUserId(Long productId, Long userId);

    List<Cart> findByUserId(Long userId);

    void deleteByProductIdInAndUserId(List<Long> productIds, Long id);
}
