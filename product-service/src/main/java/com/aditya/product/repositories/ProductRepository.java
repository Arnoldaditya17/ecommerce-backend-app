package com.aditya.product.repositories;

import com.aditya.product.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("SELECT p.skuCode FROM ProductEntity p")
    Set<String> findAllSkus();

    List<ProductEntity> findByName(String name);

    Optional<ProductEntity> findBySkuCode(String skuCode);



    }
