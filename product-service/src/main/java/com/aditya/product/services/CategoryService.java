package com.aditya.product.services;

import com.aditya.product.dtos.CategoryDto;
import com.aditya.common.dtos.CustomPageResponse;
import com.aditya.product.dtos.ProductDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CustomPageResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    CategoryDto getCategoryById(UUID id);

    void deleteCategoryById(UUID id);

    CategoryDto updateCategory(CategoryDto categoryDto, UUID id);

    CategoryDto searchCategoryByTitle(String title);

    void addProductToCategory(UUID categoryId, UUID productId);

    CategoryDto removeProductFromCategory(UUID categoryId, UUID productId);

    Set<ProductDto> getProductsByCategoryId(UUID categoryId);

}
