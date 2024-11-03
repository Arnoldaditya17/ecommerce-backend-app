package com.aditya.inventory.services;


import com.aditya.inventory.dto.InventoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    InventoryDto addInventory(InventoryDto inventoryDto);

    InventoryDto updateInventory(InventoryDto inventoryDto);

    InventoryDto getInventory(String skuCode);

    Page<InventoryDto> getAllInventory(Pageable pageable);

    void deleteInventory(String skuCode);


}
