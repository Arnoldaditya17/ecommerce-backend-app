package com.aditya.order.controllers;

import com.aditya.order.dtos.CartRequest;
import com.aditya.order.models.OrderEntity;
import com.aditya.order.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/cart")
    public ResponseEntity<OrderEntity> createCart(@RequestBody CartRequest cartRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createCart(cartRequest));
    }


}
