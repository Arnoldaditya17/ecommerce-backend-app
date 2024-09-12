package com.aditya.product.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name = "categories")
public class CategoryEntity {
    @Id
    private String id;

    private String title;

    private String description;

    private String image;

    private Date created_at;

    private Date updated_at;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new HashSet<>();



}
