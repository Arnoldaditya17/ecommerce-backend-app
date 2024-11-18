package com.aditya.product.models;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class ProductMetaData implements Serializable {
    @Serial
    private static final long serialVersionUID = -3114916239805448329L;

    private String size;
    private String color;

}
