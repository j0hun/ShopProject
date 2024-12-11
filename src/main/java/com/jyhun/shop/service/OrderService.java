package com.jyhun.shop.service;

import com.jyhun.shop.dto.OrderRequestDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.Order;
import com.jyhun.shop.entity.OrderItem;
import com.jyhun.shop.entity.Product;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.enums.OrderStatus;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.repository.OrderRepository;
import com.jyhun.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Transactional
    public ResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        User user = userService.getLoginUser();

        List<OrderItem> orderItems = orderRequestDTO.getItems().stream().map(orderItemRequest -> {
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("상품 조회 실패"));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(orderItemRequest.getQuantity())
                    .price(product.getPrice())
                    .status(OrderStatus.COMPLETED)
                    .build();
            return orderItem;
        }).collect(Collectors.toList());

        Long totalPrice = orderItems.stream()
                .map(OrderItem::calculateTotalPrice)
                .reduce(0L, Long::sum);

        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .build();

        orderItems.forEach(order::addOrderItem);
        orderRepository.save(order);

        return ResponseDTO.builder()
                .status(200)
                .message("주문 성공")
                .build();

    }

}
