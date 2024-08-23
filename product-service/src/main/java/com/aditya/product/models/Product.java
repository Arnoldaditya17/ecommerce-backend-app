package com.aditya.product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Product {

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


}
