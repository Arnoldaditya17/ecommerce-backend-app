package com.aditya.product.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class ProductMetaData {
    private Map<String, Object> additionalProperties;
}
