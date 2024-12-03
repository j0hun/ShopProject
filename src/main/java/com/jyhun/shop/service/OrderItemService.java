package com.jyhun.shop.service;

import com.jyhun.shop.dto.OrderRequest;
import com.jyhun.shop.dto.Response;
import com.jyhun.shop.entity.Order;
import com.jyhun.shop.entity.OrderItem;
import com.jyhun.shop.entity.Product;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.enums.OrderStatus;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.OrderItemRepository;
import com.jyhun.shop.repository.OrderRepository;
import com.jyhun.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final EntityDTOMapper entityDTOMapper;

    public Response createOrder(OrderRequest orderRequest) {

        User user = userService.getLoginUser();

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice() * orderItemRequest.getQuantity());
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;
        }).collect(Collectors.toList());

        Long totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice() > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(0L, Long::sum);

        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepository.save(order);

        return Response.builder()
                .status(200)
                .message("Order was successfully placed")
                .build();
    }

}
