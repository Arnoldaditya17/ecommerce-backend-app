package com.aditya.product.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProductDto implements Serializable {

    private UUID id;

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
