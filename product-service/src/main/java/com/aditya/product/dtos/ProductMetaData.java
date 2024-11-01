package com.aditya.product.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
public class ProductMetaData implements Serializable {
    @Serial
    private static final long serialVersionUID = -3114916239805448329L;
    private Map<String, Object> additionalProperties;
}
