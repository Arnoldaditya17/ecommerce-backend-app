package com.aditya.product.controller;

import com.aditya.common.dtos.CustomMessage;
import com.aditya.product.dtos.ProductDto;
import com.aditya.product.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id)
    {
        return ResponseEntity.ok(productService.getProductById(id));

    }

    @GetMapping
    public Page<ProductDto> getAllProducts(Pageable pageable)
    {
        productService.getAllProduct(pageable);
        return productService.getAllProduct(pageable);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomMessage> deleteProduct(@PathVariable String id) {
        productService.deleteProductById(id);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category deleted successfully");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        productService.updateProduct(productDto, id);
        return ResponseEntity.ok(productDto);
    }







}
