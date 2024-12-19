package com.jyhun.shop.service;

import com.jyhun.shop.dto.CategoryRequestDTO;
import com.jyhun.shop.dto.CategoryResponseDTO;
import com.jyhun.shop.dto.ResponseDTO;
import com.jyhun.shop.entity.Category;
import com.jyhun.shop.exception.NotFoundException;
import com.jyhun.shop.mapper.EntityDTOMapper;
import com.jyhun.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityDTOMapper entityDTOMapper;

    public ResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder().name(categoryRequestDTO.getName()).build();
        categoryRepository.save(category);
        return ResponseDTO.builder().status(200).message("카테고리 생성 성공").build();
    }

    public ResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리 조회 실패"));
        category.updateCategory(categoryRequestDTO.getName());
        return ResponseDTO.builder().status(200).message("카테고리 수정 성공").build();
    }

    @Transactional(readOnly = true)
    public ResponseDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리 조회 실패"));
        CategoryResponseDTO categoryResponseDTO = entityDTOMapper.mapCategoryToDTO(category);
        return ResponseDTO.builder().status(200).message("카테고리 조회 성공").data(categoryResponseDTO).build();
    }

    @Transactional(readOnly = true)
    public ResponseDTO getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryDTOList = categories.stream().map(entityDTOMapper::mapCategoryToDTO).collect(Collectors.toList());
        return ResponseDTO.builder().status(200).message("카테고리 목록 조회 성공").data(categoryDTOList).build();
    }

    public ResponseDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리 조회 실패"));
        categoryRepository.delete(category);
        return ResponseDTO.builder().status(200).message("카테고리 삭제 성공").build();
    }
}
