package com.aditya.order.services;

import com.aditya.order.dtos.CartRequest;
import com.aditya.order.models.OrderEntity;
import com.aditya.user.models.User;

public interface CartService {

	OrderEntity addToCart(OrderEntity cart, User user, CartRequest cartRequest);

	OrderEntity getCustomerCurrentCart(Integer userId);
}
