package com.jyhun.shop.service;

import com.jyhun.shop.dto.CategoryDTO;
import com.jyhun.shop.dto.Response;
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

    public Response createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    public Response updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("category updated successfully")
                .build();
    }

    @Transactional(readOnly = true)
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(entityDTOMapper::mapCategoryToDTOBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .categoryList(categoryDTOList)
                .build();
    }

    @Transactional(readOnly = true)
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        CategoryDTO categoryDTO = entityDTOMapper.mapCategoryToDTOBasic(category);
        return Response.builder()
                .status(200)
                .category(categoryDTO)
                .build();
    }

    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        categoryRepository.delete(category);
        return Response.builder()
                .status(200)
                .message("Category was deleted successfully")
                .build();
    }

}
