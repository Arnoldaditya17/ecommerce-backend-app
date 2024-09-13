package com.aditya.product.controller;

import com.aditya.common.config.AppConstants;
import com.aditya.common.dtos.CustomMessage;
import com.aditya.common.dtos.CustomPageResponse;
import com.aditya.product.dtos.CategoryDto;
import com.aditya.product.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
    }

    @PostMapping("/{categoryId}/product/{productId}")
    public void addProductToCategory(@PathVariable UUID categoryId, @PathVariable UUID productId) {
        categoryService.addProductToCategory(categoryId, productId);

    }

    @GetMapping
    public CustomPageResponse<CategoryDto> getAllCategories(
            @RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", required = false, defaultValue = AppConstants.DEFAULT_SORT_ORDER) String sortOrder) {
        return categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomMessage> deleteCategoryById(@PathVariable UUID id) {
        categoryService.deleteCategoryById(id);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category deleted successfully");
        customMessage.setSuccess(true);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customMessage);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable UUID id) {
        CategoryDto createdDto = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.status(HttpStatus.OK
        ).body(createdDto);
    }


}
