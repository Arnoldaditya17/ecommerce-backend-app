package com.aditya.order.dtos;

import com.aditya.order.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID id;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private String paymentMethod;

    private int active;

    private int quantity;

    private List<OrderItemDto> orderItems;
}
