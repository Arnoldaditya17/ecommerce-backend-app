package com.aditya.product.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryDto {


    private String id;

    @NotNull(message = "title is required !!")
    @Size(min = 3,max = 50,message = "title length should be between 3 to 50 characters !!")
    private String title;

    @NotNull
    private String description;

    private Date created_at;

}
