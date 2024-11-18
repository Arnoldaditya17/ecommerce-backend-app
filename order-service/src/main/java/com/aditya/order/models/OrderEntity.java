package com.aditya.order.models;


import com.aditya.order.constants.OrderConstants;
import com.aditya.order.enums.OrderStatus;
import com.aditya.user.models.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@Table(name = OrderConstants.ORDER_TABLE_NAME)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private LocalDateTime orderDate;

    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String razorPayOrderId;

    private boolean active;

    @JsonManagedReference
    @OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    public void addItem(OrderItemEntity item) {
        item.setOrderEntity(this);
        this.orderItems.add(item);
    }


}
