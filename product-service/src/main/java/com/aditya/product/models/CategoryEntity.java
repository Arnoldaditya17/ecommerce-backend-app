package com.aditya.product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class CategoryEntity {
    @Id
    private String id;

    private String title;

    private String description;

    private String image;

    private Date created_at;

    private Date updated_at;

    @ManyToMany(mappedBy = "categoryList")
    private List<ProductEntity> courses = new ArrayList<>();



}
