package com.aditya.order.models;


import com.aditya.order.constants.OrderConstants;
import com.aditya.order.enums.OrderStatus;
import com.aditya.user.models.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@Table(name = OrderConstants.ORDER_TABLE_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;

    private Date orderDate;

    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String razorPayOrderId;

    @Column(name = "active", columnDefinition = "int default 1")
    private int active;

    private int itemCount;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();


public void addItem(OrderItemEntity item) {
    this.orderItems.add(item);
    item.setOrderEntity(this);
}


}
