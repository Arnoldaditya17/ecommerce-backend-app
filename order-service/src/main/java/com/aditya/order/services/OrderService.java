package com.aditya.order.services;

import com.aditya.order.dtos.CartRequest;
import com.aditya.order.models.OrderEntity;
import com.aditya.user.models.User;

public interface OrderService {

	OrderEntity save(OrderEntity orderEntity);
}
