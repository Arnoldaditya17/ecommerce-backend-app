package com.aditya.inventory.dto;

import com.aditya.product.models.ProductEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Data
public class CreatedInventoryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 8087916565749526750L;

    private UUID id;

    private String skuCode;

    private Integer quantity;

    private ProductEntity product;

    private Date createdAt;

    private Date updatedAt;

}
