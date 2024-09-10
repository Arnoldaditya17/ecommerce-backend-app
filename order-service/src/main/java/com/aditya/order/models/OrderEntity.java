package com.aditya.order.models;


import com.aditya.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String id;

    private int user_id;

    private LocalDateTime orderDate;

    private String paymentMethod;

    private OrderStatus status;

    private Boolean active;

//    @ManyToOne
//    @JoinColumn(name = "shipping_address_id")
//    private ShippingAddress shippingAddress;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<OrderItems> orderItems;




}
