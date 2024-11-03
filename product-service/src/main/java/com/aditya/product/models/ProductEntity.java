package com.aditya.product.models;
import com.aditya.product.constants.ProductConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = ProductConstants.PRODUCT_TABLE_NAME)
public class ProductEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4369732139059800257L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Double marketPrice;

    private Double salePrice;

    @Column(unique = true, nullable = false)
    private String skuCode;

    private String description;

    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToMany(mappedBy = "products")
    private List<CategoryEntity> categories = new ArrayList<>();

    public void addCategory(CategoryEntity category) {
        categories.add(category);
        category.getProducts().add(this);
    }

    public void removeCategory(CategoryEntity category) {
        categories.remove(category);
        category.getProducts().remove(this);
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
