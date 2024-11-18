package com.aditya.order.models;

import com.aditya.order.constants.OrderConstants;
import com.aditya.order.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = OrderConstants.ORDER_ITEM_TABLE_NAME)
public class OrderItemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String skuCode;

    private int quantity;

    private Double mrpGrossAmount;

    private Double marketPrice;

    private Double spGrossAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderItemStatus;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private OrderEntity orderEntity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = new Date();
    }


}
