package com.aditya.product.services;

import com.aditya.product.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    Page<ProductDto> getAllProduct(Pageable pageable);

    ProductDto updateProduct(ProductDto productDto, UUID id);

    ProductDto getProductById(UUID id);

    void deleteProductById(UUID id);

    List<ProductDto> searchProduct(String keyword);


}
