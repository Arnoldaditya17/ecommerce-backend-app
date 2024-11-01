package com.aditya.inventory.repositories;

import com.aditya.inventory.models.InventoryEntity;
import com.aditya.product.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {
    Optional<InventoryEntity> findByProduct_SkuCode(String skuCode);

}
