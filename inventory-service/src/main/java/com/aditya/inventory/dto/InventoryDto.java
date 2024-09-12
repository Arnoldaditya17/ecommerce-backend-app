package com.aditya.inventory.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryDto {

    private String id;

    private String skuCode;

    private String quantity;

    private String productName;

    private String CategoryId;

    private Date createdAt;

    private Date updatedAt;
}
