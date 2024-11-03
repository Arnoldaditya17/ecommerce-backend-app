package com.aditya.inventory.models;


import com.aditya.inventory.constants.InventoryConstants;
import com.aditya.product.models.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = InventoryConstants.INVENTORY_TABLE_NAME)
public class InventoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4396187479270194982L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "quantity")
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "skuCode", referencedColumnName = "skuCode", nullable = false)
    private ProductEntity product;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
