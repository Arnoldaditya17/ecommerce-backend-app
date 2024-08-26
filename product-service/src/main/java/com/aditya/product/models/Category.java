package com.aditya.product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Category {
    @Id
    private String id;

    private String title;

    private String description;

    private String image;

    private Date created_at;

    private Date updated_at;



}
