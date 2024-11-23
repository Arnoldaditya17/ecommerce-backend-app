package com.aditya.order.services;

import com.aditya.inventory.models.InventoryEntity;
import com.aditya.inventory.repositories.InventoryRepository;
import com.aditya.inventory.services.InventoryService;
import com.aditya.order.dtos.CartRequest;
import com.aditya.order.enums.OrderStatus;
import com.aditya.order.models.OrderEntity;
import com.aditya.order.models.OrderItemEntity;
import com.aditya.order.repositories.OrderRepository;
import com.aditya.user.models.User;
import com.aditya.user.services.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	private final UserService userService;

	private final InventoryService inventoryService;

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository, InventoryRepository inventoryRepository, UserService userService,
			InventoryService inventoryService) {
		this.orderRepository = orderRepository;
		this.inventoryService = inventoryService;
		this.userService = userService;
	}

	@Override
	public OrderEntity save(OrderEntity orderEntity) {
		return orderRepository.save(orderEntity);
	}
}
