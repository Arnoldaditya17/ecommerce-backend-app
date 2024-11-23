package com.aditya.product.utils;

import com.aditya.product.models.ProductMetaData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class ProductMetaDataConverter implements AttributeConverter<ProductMetaData, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ProductMetaData metaData) {
		try {
			return objectMapper.writeValueAsString(metaData);
		} catch (JsonProcessingException jpe) {
			log.warn("Cannot convert ProductMetaData into JSON", jpe);
			return null;
		}
	}

	@Override
	public ProductMetaData convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, ProductMetaData.class);
		} catch (JsonProcessingException e) {
			log.warn("Cannot convert JSON into ProductMetaData", e);
			return null;
		}
	}
}
