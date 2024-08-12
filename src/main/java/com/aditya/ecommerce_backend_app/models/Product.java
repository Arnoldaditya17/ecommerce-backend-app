package com.aditya.ecommerce_backend_app.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    private String id;

    private String name;

    private double marketPrice;

    private double SalePrice;

    @Column(length = 6)
    private String sku;

    @Column(length = 1000)
    private String description;

    private String imageUrl;

    private String category_id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;


}
