package com.aditya.product.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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


    public void addProduct(ProductEntity product) {
        products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.getCategories().remove(this);
    }


}
