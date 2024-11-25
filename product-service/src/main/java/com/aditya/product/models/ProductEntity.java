package com.aditya.product.models;

import com.aditya.product.constants.ProductConstants;

import com.aditya.product.utils.ProductMetaDataConverter;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Slf4j
@Table(name = ProductConstants.PRODUCT_TABLE_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4369732139059800257L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "market_price")
    private Double marketPrice;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "sku_code", unique = true, nullable = false)
    private String skuCode;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Convert(converter = ProductMetaDataConverter.class)
    private ProductMetaData productMetaData;

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CategoryEntity> categories = new HashSet<>();

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
