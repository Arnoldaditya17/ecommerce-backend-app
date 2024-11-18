package com.aditya.product.dtos;

import com.aditya.product.models.CategoryEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7308142147929518929L;

    private UUID id;

    private String name;

    private Double marketPrice;

    private Double salePrice;

    private String skuCode;

    private String description;

    private String image;

    private Date createdAt;

    private Date updatedAt;

    private Set<CategoryEntity> categories;
}
