package com.aditya.inventory.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class InventoryDto {

    private UUID id;

    private String skuCode;

    private String quantity;

    private String name;

    private String categoryId;

    private Date createdAt;

    private Date updatedAt;
}
