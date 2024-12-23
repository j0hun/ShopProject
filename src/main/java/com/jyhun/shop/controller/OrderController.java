package com.jyhun.shop.controller;

import com.jyhun.shop.dto.OrderRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO));
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseDTO> getOrderHistory() {
        return ResponseEntity.ok(orderService.getOrderHistory());
    }

}
