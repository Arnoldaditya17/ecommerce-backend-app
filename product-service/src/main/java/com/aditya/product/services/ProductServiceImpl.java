package com.aditya.product.services;


import com.aditya.common.utils.EntityDtoMapper;
import com.aditya.product.dtos.ProductDto;
import com.aditya.product.models.Product;
import com.aditya.product.repositories.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final EntityDtoMapper entityDtoMapper;
   private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, EntityDtoMapper entityDtoMapper, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        String productId= UUID.randomUUID().toString();
        productDto.setId(productId);
        productDto.setCreatedAt(new Date());
       Product savedproduct = productRepository.save(entityDtoMapper.toEntity(productDto, Product.class));
       return entityDtoMapper.toDto(savedproduct, ProductDto.class);
    }

    @Override
    public Page<ProductDto> getAllProduct(Pageable pageable) {
       Page<Product> products = productRepository.findAll(pageable);
       List<ProductDto> dtos =products.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        return new PageImpl<>(dtos,pageable,products.getTotalElements());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String id) {
        Product product=productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productDto.setUpdatedAt(new Date());
        modelMapper.map(productDto, product);
       Product updatedProduct = productRepository.save(product);
        return entityDtoMapper.toDto(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product=productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return entityDtoMapper.toDto(product, ProductDto.class);
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(id);

    }

    @Override
    public List<ProductDto> searchProduct(String keyword) {
        return List.of();
    }
}
