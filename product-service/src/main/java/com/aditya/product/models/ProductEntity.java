package com.aditya.product.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
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

    private  int marketPrice;

    private int salePrice;

    private String sku;

    private String description;

    private String image;

    private String category;

    private Date created_at;

    private Date updated_at;

    @ManyToMany
    private List<CategoryEntity> categoryList = new ArrayList<>();





}
