package com.aditya.order.models;

import com.aditya.inventory.models.InventoryEntity;
import com.aditya.order.constants.OrderConstants;
import com.aditya.order.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = OrderConstants.ORDER_ITEM_TABLE_NAME)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column()
	private String skuCode;

	@Column()
	private int quantity;

	@Column()
	private Double marketPrice = 0d;

	@Column()
	private Double salePrice = 0d;

	@Column()
	private Double mrpGrossAmount = 0d;

	@Column()
	private Double spGrossAmount = 0d;

	@Column()
	private Double finalAmount = 0d;

	@Column()
	private Double totalDiscountAmount = 0d;

	@Enumerated(EnumType.STRING)
	private OrderStatus itemStatus;

	@Column(name = "order_id")
	private UUID orderId;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity orderEntity;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Date();
	}

	public static OrderItemEntity createOrderItem(InventoryEntity inventory, int quantity) {
		OrderItemEntity orderItem = newInstance();
		orderItem.setSkuCode(inventory.getSkuCode());
		orderItem.setMarketPrice(inventory.getMarketPrice());
		orderItem.setSalePrice(inventory.getSalePrice());
		orderItem.setQuantity(quantity);
		return orderItem;
	}

	public static OrderItemEntity newInstance() {
		return new OrderItemEntity();
	}

}
