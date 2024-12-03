package com.jyhun.shop.service;

import com.jyhun.shop.dto.ProductDTO;
import com.jyhun.shop.dto.Response;
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

    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, Long price) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        String productImageUrl = localStorageService.saveImageToLocal(image);

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepository.save(product);
        return Response.builder().status(200).message("Product successfully created").build();
    }

    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, Long price) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));

        Category category = null;
        String productImageUrl = null;

        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        }

        if (image != null && !image.isEmpty()) {
            productImageUrl = localStorageService.saveImageToLocal(image);
        }

        if (category != null) product.setCategory(category);
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);
        if (productImageUrl != null) product.setImageUrl(productImageUrl);

        productRepository.save(product);

        return Response.builder().status(200).message("Product Updated successfully").build();
    }

    public Response deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        productRepository.delete(product);

        return Response.builder().status(200).message("Product deleted successfully").build();
    }

    @Transactional(readOnly = true)
    public Response getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        ProductDTO productDTO = entityDTOMapper.mapProductToDTOBasic(product);
        return Response.builder().status(200).product(productDTO).build();
    }

    @Transactional(readOnly = true)
    public Response getAllProducts() {
        List<ProductDTO> productDTOList = productRepository.findAll().stream().map(entityDTOMapper::mapProductToDTOBasic).collect(Collectors.toList());

        return Response.builder().status(200).productList(productDTOList).build();
    }

    @Transactional(readOnly = true)
    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found form this category");
        }
        List<ProductDTO> productDTOList = products.stream().map(entityDTOMapper::mapProductToDTOBasic).collect(Collectors.toList());

        return Response.builder().status(200).productList(productDTOList).build();
    }

    @Transactional(readOnly = true)
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()) {
            throw new NotFoundException("No Products Found");
        }

        List<ProductDTO> productDTOList = products.stream().map(entityDTOMapper::mapProductToDTOBasic).collect(Collectors.toList());

        return Response.builder().status(200).productList(productDTOList).build();
    }

}
