package com.aditya.product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(mappedBy = "categoryList")
    private List<Product> courses = new ArrayList<>();



}
