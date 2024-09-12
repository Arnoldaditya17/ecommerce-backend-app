package com.aditya.inventory.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class InventoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String skuCode;

    private String productName;

    private String categoryId;

    private Integer quantity;

    private Date createdAt;

    private Date updatedAt;

}
