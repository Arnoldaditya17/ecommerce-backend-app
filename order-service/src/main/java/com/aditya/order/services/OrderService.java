package com.aditya.order.services;

import com.aditya.order.dtos.CartRequest;
import com.aditya.order.models.OrderEntity;

public interface OrderService {

    OrderEntity createCart(CartRequest cartRequest);
}
