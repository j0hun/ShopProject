package com.jyhun.shop.controller;

import com.jyhun.shop.dto.CartRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{productId}")
    public ResponseEntity<ResponseDTO> createCart(@RequestBody CartRequestDTO cartRequestDTO, @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.createCart(cartRequestDTO, productId));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getMyCart() {
        return ResponseEntity.ok(cartService.getMyCart());
    }

}
