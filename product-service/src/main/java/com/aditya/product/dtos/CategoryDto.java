package com.aditya.product.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class CategoryDto implements Serializable {

    private UUID id;

    @NotNull(message = "title is required !!")
    @Size(min = 3, max = 50, message = "title length should be between 3 to 50 characters !!")
    private String title;

    @NotNull
    private String description;

    private Date created_at;

    private Date updated_at;

}
