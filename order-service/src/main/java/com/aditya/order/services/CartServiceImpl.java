package com.aditya.order.services;

import com.aditya.inventory.models.InventoryEntity;
import com.aditya.inventory.services.InventoryService;
import com.aditya.order.dtos.CartRequest;
import com.aditya.order.enums.OrderStatus;
import com.aditya.order.models.OrderEntity;
import com.aditya.order.models.OrderItemEntity;
import com.aditya.order.repositories.OrderRepository;
import com.aditya.user.models.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

	private final InventoryService inventoryService;

	private final OrderRepository orderRepository;

	private final PricingService pricingService;

	public CartServiceImpl(InventoryService inventoryService, OrderRepository orderRepository, PricingService pricingService) {
		this.inventoryService = inventoryService;
		this.orderRepository = orderRepository;
		this.pricingService = pricingService;
	}

	@Override
	public OrderEntity addToCart(OrderEntity cart, User user, CartRequest request) {
		InventoryEntity inventory = inventoryService.findBySkuCode(request.getSkuCode())
				.orElseThrow(() -> new RuntimeException("Product not found for SKU: " + request.getSkuCode()));
		// Fetch or create a cart
		addItemToCart(inventory, cart, request.getQuantity());

		// Save the cart and return
		return orderRepository.save(cart);
	}

	public void addItemToCart(InventoryEntity inventoryItem, OrderEntity cart, int quantity) {
		OrderItemEntity cartItem = getCartItemBySkuCode(cart, inventoryItem.getSkuCode());
		if (cartItem == null) {
			cartItem = OrderItemEntity.createOrderItem(inventoryItem, quantity);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		validateInventory(inventoryItem, quantity, cartItem);
		if (CollectionUtils.isEmpty(cart.getOrderItems())) {
			cart.setOrderItems(List.of(cartItem));
		} else {
			cart.getOrderItems().add(cartItem);
		}
		pricingService.setAmountAndTaxesInOrderAndItem(cart);
	}

	public OrderItemEntity getCartItemBySkuCode(OrderEntity cart, String skuCode) {
		if (cart == null || cart.getOrderItems() == null) {
			return null;
		}
		return cart.getOrderItems().stream().filter(item -> item.getSkuCode().equals(skuCode)).findFirst().orElse(null);
	}

	public OrderEntity getCustomerCurrentCart(Integer customerId) {
		return orderRepository.findByUserIdAndStatus(customerId, OrderStatus.IN_CART);
	}

	private void validateInventory(InventoryEntity inventory, Integer requestQuantity, OrderItemEntity existingItem) {
		if (existingItem != null && requestQuantity < existingItem.getQuantity()) {
			return;
		}
		if (inventory.getQuantity() < requestQuantity) {
			throw new RuntimeException("Insufficient stock for SKU: " + inventory.getSkuCode());
		}
	}
}
