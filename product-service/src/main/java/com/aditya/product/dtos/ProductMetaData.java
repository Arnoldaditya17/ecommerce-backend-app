package com.aditya.product.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ProductMetaData implements Serializable {
    private Map<String, Object> additionalProperties;
}
