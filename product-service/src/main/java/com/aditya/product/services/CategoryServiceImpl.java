package com.aditya.product.services;


import com.aditya.common.exceptions.ResourceNotFoundException;
import com.aditya.common.utils.EntityDtoMapper;
import com.aditya.product.dtos.CategoryDto;
import com.aditya.common.dtos.CustomPageResponse;
import com.aditya.product.dtos.ProductDto;
import com.aditya.product.models.CategoryEntity;
import com.aditya.product.models.ProductEntity;
import com.aditya.product.repositories.CategoryRepository;
import com.aditya.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final EntityDtoMapper entityDtoMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, EntityDtoMapper entityDtoMapper, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        categoryDto.setCreated_at(new Date());
        CategoryEntity category = entityDtoMapper.toEntity(categoryDto, CategoryEntity.class);
        CategoryEntity savedCategory = categoryRepository.save(category);

        return entityDtoMapper.toDto(savedCategory, CategoryDto.class);
    }

    @Override
    public CustomPageResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageRequest);
        List<CategoryEntity> categoryList = categoryPage.getContent();

        List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> entityDtoMapper.toDto(category, CategoryDto.class)).toList();
        CustomPageResponse<CategoryDto> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setPageSize(pageSize);
        customPageResponse.setLast(categoryPage.isLast());
        customPageResponse.setTotalElements(categoryPage.getTotalElements());
        customPageResponse.setTotalPages(categoryPage.getTotalPages());
        customPageResponse.setContent(categoryDtoList);

        return customPageResponse;
    }

    @Override
    public CategoryDto getCategoryById(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category not found !", id));

        return entityDtoMapper.toDto(category, CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category not found !", id));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category not found !", id));
        modelMapper.map(categoryDto, category);
        CategoryEntity savedCategory = categoryRepository.save(category);

        return entityDtoMapper.toDto(savedCategory, CategoryDto.class);

    }

    @Override
    public CategoryDto searchCategoryByTitle(String title) {

        return null;
    }

    @Override
    public void addProductToCategory(UUID categoryId, UUID productId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found !", categoryId));
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found !", productId));

        category.addProduct(product);
        categoryRepository.save(category);
        System.out.println("Added product to Category ❤❤❤❤");

    }

    @Override
    @Transactional
    public List<ProductDto> getProductOfCateg(UUID categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found", categoryId));
        List<ProductEntity> productEntityList = categoryEntity.getProducts();

        return productEntityList.stream().map(product -> entityDtoMapper.toDto(product,ProductDto.class)).toList();
    }
}
