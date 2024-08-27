package com.aditya.product.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Product  {

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
    private List<Category> categoryList = new ArrayList<>();





}
