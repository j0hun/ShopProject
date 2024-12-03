package com.jyhun.shop.controller;

import com.jyhun.shop.dto.OrderRequest;
import com.jyhun.shop.dto.Response;
import com.jyhun.shop.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public ResponseEntity<Response> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderItemService.createOrder(orderRequest));
    }

}
