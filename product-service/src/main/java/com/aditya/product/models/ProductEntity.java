package com.aditya.product.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class ProductEntity {

    @Id
    private String id;

    private String name;

    private int marketPrice;

    private int salePrice;

    @Column(unique = true, nullable = false)
    private String skuCode;

    private String description;

    private String image;

    private Date created_at;

    private Date updated_at;

    @ManyToMany(mappedBy = "products")
    private List<CategoryEntity> categories = new ArrayList<>();


}
