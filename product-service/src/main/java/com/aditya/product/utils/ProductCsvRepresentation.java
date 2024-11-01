package com.aditya.product.utils;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCsvRepresentation {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "description")
    private String description;

    @CsvBindByName(column = "market_price")
    private double marketPrice;

    @CsvBindByName(column = "image")
    private String image;

    @CsvBindByName(column = "sku_code" )
    private String skuCode;

    @CsvBindByName(column = "sale_price" )
    private double salePrice;

    @CsvBindByName(column = "created_at")
    private Date createdAt;

    @CsvBindByName(column = "updated_at")
    private Date updatedAt;



}
