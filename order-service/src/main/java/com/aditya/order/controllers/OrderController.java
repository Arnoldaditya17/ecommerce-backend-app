package com.aditya.order.controllers;

import com.aditya.order.dtos.OrderDto;
import com.aditya.order.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/add-to-cart")
    public ResponseEntity<OrderDto>  createOrder(OrderDto orderDto) {
        return null;
    }



}
