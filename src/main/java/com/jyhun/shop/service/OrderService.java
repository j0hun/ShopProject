package com.jyhun.shop.service;

import com.jyhun.shop.dto.OrderRequestDTO;
import com.jyhun.shop.dto.OrderResponseDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.*;
import com.jyhun.shop.enums.OrderStatus;
import com.jyhun.shop.exception.InsufficientBalanceException;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.CartRepository;
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
    private final PaymentService paymentService;
    private final CartRepository cartRepository;
    private final EntityDTOMapper entityDTOMapper;

    @Transactional
    public ResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        User user = userService.getLoginUser();

        List<OrderItem> orderItems = orderRequestDTO.getItems().stream().map(orderItemRequest -> {
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("상품 조회 실패"));

            product.decreaseStock(orderItemRequest.getQuantity());

            return OrderItem.builder()
                    .product(product)
                    .quantity(orderItemRequest.getQuantity())
                    .price(product.getPrice())
                    .status(OrderStatus.COMPLETED)
                    .build();
        }).collect(Collectors.toList());

        Long totalPrice = orderItems.stream()
                .map(OrderItem::calculateTotalPrice)
                .reduce(0L, Long::sum);

        if (user.getBalance() < totalPrice) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }
        user.updateBalance(user.getBalance() - totalPrice);

        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .build();

        orderItems.forEach(order::addOrderItem);

        Payment payment = paymentService.createPayment(totalPrice);
        order.changePayment(payment);

        if (orderRequestDTO.getOrderType().equals("cart")) {
            List<Long> productIds = orderRequestDTO.getItems().stream()
                    .map(orderItemRequest -> orderItemRequest.getProductId())
                    .collect(Collectors.toList());
            cartRepository.deleteByProductIdInAndUserId(productIds, user.getId());
        }
        orderRepository.save(order);

        return ResponseDTO.builder()
                .status(200)
                .message("주문 성공")
                .build();

    }

    @Transactional(readOnly = true)
    public ResponseDTO getOrderHistory() {
        User user = userService.getLoginUser();
        List<Order> orderList = orderRepository.findAllByUserId(user.getId());
        List<OrderResponseDTO> orderResponseDTOList = entityDTOMapper.mapOrderListToDTOList(orderList);

        return ResponseDTO.builder()
                .status(200)
                .message("주문내역 조회 성공")
                .data(orderResponseDTOList)
                .build();

    }
}
