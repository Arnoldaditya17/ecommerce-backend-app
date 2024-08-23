package com.aditya.product.controller;

import com.aditya.product.models.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Categories")
public class CategoryController {
    @GetMapping
    public List<Category> getCategories() {
        return List.of();
    }
}
