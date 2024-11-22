package com.aditya.order.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDto {

    private UUID id;

    private String skuCode;

    private int quantity;

    private Double marketPrice;

    private Double mrpGrossAmount;

    private Double spGrossAmount;

}
