package com.aditya.order.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class OrderItems {


    @Id
    private String id;

    private String productId;

    private String productSku;

    private int quantity;

    private int mrp;

    private  int salePrice;

    private Date orderDate;

    private Date createTime;

    private Date updateTime;




}
