package com.aditya.order.dtos;

import lombok.Data;

@Data
public class CartRequest {

	private String skuCode;

	private int quantity;

}
