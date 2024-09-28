package com.aditya.inventory.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "inventories")
public class InventoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String skuCode;

    private String name;

    private String categoryId;

    private Integer quantity;

    private Date createdAt;

    private Date updatedAt;

}
