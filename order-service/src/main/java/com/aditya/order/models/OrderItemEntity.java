package com.aditya.order.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "order_items")
public class OrderItemEntity {


    @Id
    private String id;

    private String productSku;

    private int quantity;

    private int mrp;

    private  int salePrice;

    private Date createTime;

    private Date updateTime;






}
