package com.aditya.ecommerce_backend_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcommerceBackendAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendAppApplication.class, args);
	}

}
