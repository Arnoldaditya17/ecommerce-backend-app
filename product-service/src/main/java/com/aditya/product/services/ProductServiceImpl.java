package com.aditya.product.services;

import com.aditya.common.exceptions.ResourceNotFoundException;
import com.aditya.common.utils.EntityDtoMapper;
import com.aditya.product.dtos.ProductDto;
import com.aditya.product.models.ProductEntity;
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
        productDto.setCreatedAt(new Date());
        ProductEntity savedProduct = productRepository.save(entityDtoMapper.toEntity(productDto, ProductEntity.class));
        return entityDtoMapper.toDto(savedProduct, ProductDto.class);
    }

    @Override
    public Page<ProductDto> getAllProduct(Pageable pageable) {
        Page<ProductEntity> products = productRepository.findAll(pageable);
        List<ProductDto> dtos = products.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        return new PageImpl<>(dtos, pageable, products.getTotalElements());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", id));
        productDto.setUpdatedAt(new Date());
        modelMapper.map(productDto, product);
        ProductEntity updatedProduct = productRepository.save(product);
        return entityDtoMapper.toDto(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", id));
        return entityDtoMapper.toDto(product, ProductDto.class);
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", id));
        productRepository.deleteById(id);

    }

    @Override
    public List<ProductDto> searchProduct(String keyword) {
        return List.of();
    }
}
