package com.aditya.inventory.services;


import com.aditya.inventory.dto.CreatedInventoryDto;
import com.aditya.inventory.dto.InventoryDto;
import com.aditya.inventory.models.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InventoryService {

    CreatedInventoryDto createInventory(CreatedInventoryDto createdInventoryDto);

    InventoryDto updateInventory(InventoryDto inventoryDto);

    InventoryDto getInventory(String skuCode);

    Page<CreatedInventoryDto> getAllInventory(Pageable pageable);

    void deleteInventory(String skuCode);

	Optional<InventoryEntity> findBySkuCode(String skuCode);
}
