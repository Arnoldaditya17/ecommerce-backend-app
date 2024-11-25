package com.aditya.order.services;

import com.aditya.order.models.OrderEntity;
import com.aditya.order.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public OrderEntity save(OrderEntity orderEntity) {
		return orderRepository.save(orderEntity);
	}
}
