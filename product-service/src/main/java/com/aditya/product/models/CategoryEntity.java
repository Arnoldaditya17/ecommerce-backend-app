package com.aditya.product.models;

import com.aditya.product.constants.CategoryConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Table(name = CategoryConstants.CATEGORY_TABLE_NAME)
public class CategoryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 218451937471973394L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products = new ArrayList<>();


    public void addProduct(ProductEntity product) {
        products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.getCategories().remove(this);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }


}
