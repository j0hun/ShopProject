package com.jyhun.shop.service;

import com.jyhun.shop.dto.CartRequestDTO;
import com.jyhun.shop.dto.CartResponseDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.Cart;
import com.jyhun.shop.entity.Product;
import com.jyhun.shop.entity.User;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.CartRepository;
import com.jyhun.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final EntityDTOMapper entityDTOMapper;

    @Transactional
    public ResponseDTO createCart(CartRequestDTO cartRequestDTO, Long productId) {
        User user = userService.getLoginUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품 조회 실패"));
        Cart cart = cartRepository.findByProductIdAndUserId(productId, user.getId()).orElse(null);
        if(cart != null) {
            cart.updateQuantity(cart.getQuantity() + cartRequestDTO.getQuantity());
        }else {
            Cart newCart = Cart.builder()
                    .user(user)
                    .product(product)
                    .quantity(cartRequestDTO.getQuantity())
                    .price(product.getPrice())
                    .build();
            cartRepository.save(newCart);
        }

        return ResponseDTO.builder()
                .status(200)
                .message("장바구니 담기 성공")
                .build();

    }

    public ResponseDTO getMyCart() {
        User user = userService.getLoginUser();
        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        List<CartResponseDTO> cartResponseDTOList = entityDTOMapper.mapCartListToDTO(cartList);
        return ResponseDTO.builder()
                .status(200)
                .message("내 장바구니 보기 성공")
                .data(cartResponseDTOList)
                .build();

    }
}
