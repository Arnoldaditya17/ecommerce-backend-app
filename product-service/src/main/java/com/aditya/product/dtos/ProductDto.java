package com.aditya.product.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductDto {

    private String id;

    private String name;

    private int marketPrice;

    private int salePrice;

    private String skuCode;

    private String description;

    private String image;

    private String category;

    private Date createdAt;

    private Date updatedAt;

    private List<String> categories;
}
