package com.jyhun.shop.service;

import com.jyhun.shop.dto.ProductDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.Category;
import com.jyhun.shop.entity.Product;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.CategoryRepository;
import com.jyhun.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EntityDTOMapper entityDTOMapper;
    private final LocalStorageService localStorageService;

    public ResponseDTO createProduct(Long categoryId, MultipartFile image, String name, String description, Long price) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리 조회 실패"));
        String productImageUrl = localStorageService.saveImageToLocal(image);

        Product product = Product.builder()
                .category(category)
                .price(price)
                .name(name)
                .description(description)
                .imageUrl(productImageUrl)
                .build();

        productRepository.save(product);
        return ResponseDTO.builder().status(200).message("상품 생성 성공").build();
    }

    public ResponseDTO updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, Long price) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품 조회 실패"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리 조회 실패"));

        String productImageUrl = null;

        if (image != null && !image.isEmpty()) {
            productImageUrl = localStorageService.saveImageToLocal(image);
        }

        product.updateProduct(name, description, productImageUrl, price, category);
        return ResponseDTO.builder().status(200).message("상품 수정 성공").build();
    }

    public ResponseDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품 조회 실패"));
        productRepository.delete(product);

        return ResponseDTO.builder().status(200).message("상품 삭제 성공").build();
    }

    @Transactional(readOnly = true)
    public ResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품 조회 실패"));
        ProductDTO productDTO = entityDTOMapper.mapProductToDTO(product);
        return ResponseDTO.builder().status(200).message("상품 조회 성공").data(productDTO).build();
    }

    @Transactional(readOnly = true)
    public ResponseDTO getAllProducts() {
        List<ProductDTO> productDTOList = productRepository.findAll().stream().map(entityDTOMapper::mapProductToDTO).collect(Collectors.toList());
        return ResponseDTO.builder().status(200).message("상품 목록 조회 성공").data(productDTOList).build();
    }

    @Transactional(readOnly = true)
    public ResponseDTO getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("카테고리에 상품 목록이 없습니다.");
        }
        List<ProductDTO> productDTOList = products.stream().map(entityDTOMapper::mapProductToDTO).collect(Collectors.toList());
        return ResponseDTO.builder().status(200).message("카테고리의 상품 목록 조회 성공").data(productDTOList).build();
    }

    @Transactional(readOnly = true)
    public ResponseDTO searchProduct(String searchValue) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue);
        if (products.isEmpty()) {
            throw new NotFoundException("상품이 없습니다.");
        }
        List<ProductDTO> productDTOList = products.stream().map(entityDTOMapper::mapProductToDTO).collect(Collectors.toList());
        return ResponseDTO.builder().status(200).message("상품 검색 성공").data(productDTOList).build();
    }

}
