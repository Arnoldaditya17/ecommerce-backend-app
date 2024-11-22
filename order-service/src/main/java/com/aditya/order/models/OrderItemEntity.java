package com.aditya.order.models;

import com.aditya.order.constants.OrderConstants;
import com.aditya.order.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = OrderConstants.ORDER_ITEM_TABLE_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String skuCode;

    private int quantity;

    private Double mrpGrossAmount = 0d;

    private Double marketPrice;

    private Double spGrossAmount = 0d;

    @Enumerated(EnumType.STRING)
    private OrderStatus itemStatus;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
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
