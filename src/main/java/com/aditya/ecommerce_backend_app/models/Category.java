package com.aditya.ecommerce_backend_app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    private String id;

    private String name;

    private String description;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
