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

	@Column(name = "sku_code")
	private String skuCode;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "market_price")
	private Double marketPrice;

	@Column(name = "sale_price")
	private Double salePrice;

	@Column(name = "mrp_gross_amount")
	private Double mrpGrossAmount;

	@Column(name = "sp_gross_amount")
	private Double spGrossAmount;

	@Column(name = "final_amount")
	private Double finalAmount;

	@Column(name = "total_discount_amount")
	private Double totalDiscountAmount;

	@Enumerated(EnumType.STRING)
	private OrderStatus itemStatus;

	@Column(name = "order_id", insertable = false, updatable = false)
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
