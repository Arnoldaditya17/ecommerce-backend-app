package com.aditya.ecommerce_backend_app;

import com.aditya.product.services.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.UUID;


@SpringBootTest
public class EcommerceBackendAppApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void contextLoads() {

    }

    @Test
    public void CategoryProductRelation() {
        UUID productId = UUID.fromString("61e3da48-e316-40d4-b867-9afd7f0859ff");
        UUID categoryId = UUID.fromString("204aaacf-ba3a-40d9-a720-bb26a953c450");
        categoryService.addProductToCategory(categoryId, productId);

    }

}
