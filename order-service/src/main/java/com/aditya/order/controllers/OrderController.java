package com.aditya.order.controllers;

import com.aditya.order.dtos.CartRequest;
import com.aditya.order.enums.OrderStatus;
import com.aditya.order.models.OrderEntity;
import com.aditya.order.services.CartService;
import com.aditya.user.models.User;
import com.aditya.user.services.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final CartService cartService;

	private final UserService userService;

	public OrderController(CartService cartService, UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}

	@PostMapping("/cart")
	public ResponseEntity<OrderEntity> createCart(@RequestBody CartRequest cartRequest) {
		User user = userService.getCurrentSessionUser();

		OrderEntity cart = cartService.getCustomerCurrentCart(user.getId());
		if (cart == null) {
			cart = new OrderEntity();
			cart.setUser(user);
			cart.setStatus(OrderStatus.IN_CART);
		}
		cart = cartService.addToCart(cart, user, cartRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(cart);
	}

}
