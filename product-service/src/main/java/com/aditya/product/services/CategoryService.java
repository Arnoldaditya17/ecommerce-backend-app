package com.aditya.product.services;


import com.aditya.product.dtos.CategoryDto;
import com.aditya.common.dtos.CustomPageResponse;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CustomPageResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    CategoryDto getCategoryById(String id);

    void deleteCategoryById(String id);

    CategoryDto updateCategory(CategoryDto categoryDto , String id);

    CategoryDto searchCategoryByTitle(String title);

    void addProductToCategory(String categoryId, String productId);
}
