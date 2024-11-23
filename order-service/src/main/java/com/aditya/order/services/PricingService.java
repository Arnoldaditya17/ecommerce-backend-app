package com.aditya.order.services;

import com.aditya.order.models.OrderEntity;
import com.aditya.order.models.OrderItemEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PricingService {

	private final OrderService orderService;

	public PricingService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setAmountAndTaxesInOrderAndItem(OrderEntity order) {
		if (order == null || CollectionUtils.isEmpty(order.getOrderItems())) {
			return;
		}
		setAmountAndTaxesInItem(order.getOrderItems());
		setAmountAndTaxesInOrder(order);
		orderService.save(order);
	}

	private void setAmountAndTaxesInOrder(OrderEntity order) {
		BigDecimal mrpGrossAmount = BigDecimal.ZERO;
		BigDecimal spGrossAmount = BigDecimal.ZERO;
		BigDecimal totalDiscountAmount = BigDecimal.ZERO;
		BigDecimal finalBillAmount = BigDecimal.ZERO;
		for (OrderItemEntity orderItem : order.getOrderItems()) {
			mrpGrossAmount = mrpGrossAmount.add(BigDecimal.valueOf(orderItem.getMarketPrice()));
			spGrossAmount = spGrossAmount.add(BigDecimal.valueOf(orderItem.getSalePrice()));
			totalDiscountAmount = totalDiscountAmount.add(BigDecimal.valueOf(orderItem.getTotalDiscountAmount()));
			finalBillAmount = finalBillAmount.add(BigDecimal.valueOf(orderItem.getFinalAmount()));
		}

		order.setMrpGrossAmount(setScale(mrpGrossAmount));
		order.setSpGrossAmount(setScale(spGrossAmount));
		order.setTotalDiscountAmount(setScale(totalDiscountAmount));
		order.setFinalBillAmount(setScale(finalBillAmount));
	}

	private void setAmountAndTaxesInItem(List<OrderItemEntity> orderItems) {
		for (OrderItemEntity orderItem : orderItems) {
			BigDecimal salePrice = BigDecimal.valueOf(orderItem.getSalePrice());
			BigDecimal marketPrice = BigDecimal.valueOf(orderItem.getMarketPrice());
			BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());
			BigDecimal spGrossAmount = salePrice.multiply(quantity);
			BigDecimal mpGrossAmount = marketPrice.multiply(quantity);
			BigDecimal discount = mpGrossAmount.subtract(spGrossAmount);

			BigDecimal finalAmount = spGrossAmount;

			orderItem.setSpGrossAmount(setScale(spGrossAmount));
			orderItem.setMrpGrossAmount(setScale(mpGrossAmount));
			orderItem.setTotalDiscountAmount(setScale(discount));
			orderItem.setFinalAmount(setScale(finalAmount));
		}
	}

	public Double setScale(BigDecimal value) {
		return value.setScale(0, RoundingMode.HALF_UP).doubleValue();
	}
}
