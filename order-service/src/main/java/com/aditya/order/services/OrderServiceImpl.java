package com.aditya.order.services;

import com.aditya.inventory.models.InventoryEntity;
import com.aditya.inventory.repositories.InventoryRepository;
import com.aditya.order.dtos.CartRequest;
import com.aditya.order.enums.OrderStatus;
import com.aditya.order.models.OrderEntity;
import com.aditya.order.models.OrderItemEntity;
import com.aditya.order.repositories.OrderRepository;
import com.aditya.user.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final InventoryRepository inventoryRepository;

	public OrderServiceImpl(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
		this.orderRepository = orderRepository;
		this.inventoryRepository = inventoryRepository;
	}

	@Override
	public OrderEntity createCart(CartRequest cartRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();

		InventoryEntity inventory = inventoryRepository.findByProduct_SkuCode(cartRequest.getSkuCode())
				.orElseThrow(() -> new RuntimeException("Product not found for SKU: " + cartRequest.getSkuCode()));

		if (inventory.getQuantity() < cartRequest.getQuantity()) {
			throw new RuntimeException("Insufficient stock for SKU: " + cartRequest.getSkuCode());
		}

		// Fetch or create a cart
		OrderEntity cart = orderRepository.findByUserIdAndStatus(currentUser.getId(), OrderStatus.IN_CART);
		if (cart == null) {
			cart = new OrderEntity();
			cart.setUser(currentUser);
			cart.setStatus(OrderStatus.IN_CART);
		}

		OrderItemEntity existingItem = cart.getOrderItems().stream().filter(item -> item.getSkuCode().equals(cartRequest.getSkuCode())).findFirst()
				.orElse(null);

		if (existingItem != null) {
			// Update quantity if item exists
			existingItem.setQuantity(existingItem.getQuantity() + cartRequest.getQuantity());
		} else {
			// Add new item if it doesn't exist
			OrderItemEntity newItem = new OrderItemEntity();
			newItem.setOrderEntity(cart);
			newItem.setSkuCode(cartRequest.getSkuCode());
			newItem.setQuantity(cartRequest.getQuantity());
			cart.addItem(newItem);
		}
		// Save the cart and return
		return orderRepository.save(cart);
	}

}
