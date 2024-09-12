package com.aditya.product.services;

import com.aditya.product.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    Page< ProductDto> getAllProduct(Pageable pageable);

    ProductDto updateProduct( ProductDto productDto,String id);

    ProductDto getProductById(String id);

    void deleteProductById(String id);

    List< ProductDto> searchProduct(String keyword);





}
