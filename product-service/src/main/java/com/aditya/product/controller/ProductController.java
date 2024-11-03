package com.aditya.product.controller;

import com.aditya.common.dtos.CustomMessage;
import com.aditya.product.dtos.ProductDto;
import com.aditya.product.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productDto));
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadStudents(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.uploadFile(file));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));

    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        Page<ProductDto> products = productService.getAllProduct(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/{keyword}")
    ResponseEntity<List<ProductDto>> searchProductsByKeyword(@PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProduct(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomMessage> deleteProduct(@PathVariable UUID id) {
        productService.deleteProductById(id);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("product deleted successfully");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customMessage);
    }

    @DeleteMapping()
    public ResponseEntity<CustomMessage> deleteAllProducts() {
        productService.deleteAllProducts();
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("all product deleted successfully");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CustomMessage());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productDto, id);
        return ResponseEntity.ok(updatedProduct);
    }
}
