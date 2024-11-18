package com.aditya.inventory.services;


import com.aditya.inventory.dto.CreatedInventoryDto;
import com.aditya.inventory.dto.InventoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    CreatedInventoryDto addInventory(CreatedInventoryDto createdInventoryDto);

    InventoryDto updateInventory(InventoryDto inventoryDto);

    InventoryDto getInventory(String skuCode);

    Page<CreatedInventoryDto> getAllInventory(Pageable pageable);

    void deleteInventory(String skuCode);


}
