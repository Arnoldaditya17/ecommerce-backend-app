package com.aditya.ecommerce_backend_app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.aditya.user", "com.aditya.ecommerce_backend_app"})
@EnableJpaRepositories(basePackages = "com.aditya.user.repositories")
@EntityScan(basePackages = "com.aditya.user.models")
@EnableCaching
public class EcommerceBackendAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendAppApplication.class, args);
    }

}
