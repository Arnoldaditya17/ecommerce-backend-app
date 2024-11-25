package com.aditya.inventory.models;

import com.aditya.inventory.constants.InventoryConstants;
import com.aditya.product.models.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = InventoryConstants.INVENTORY_TABLE_NAME)
public class InventoryEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 4396187479270194982L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "market_price")
	private Double marketPrice;

	@Column(name = "sale_price")
	private Double salePrice;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(name = "sku_code", insertable = false, updatable = false)
	private String skuCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sku_code", referencedColumnName = "sku_code", nullable = false)
	private ProductEntity product;

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Date();
	}

}
