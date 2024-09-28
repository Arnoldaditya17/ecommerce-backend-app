package com.aditya.inventory.services;


import com.aditya.inventory.dto.InventoryDto;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    InventoryDto getInventory(UUID id);

    List<InventoryDto> getAllInventories();

    InventoryDto addInventory(InventoryDto inventoryDto);

    InventoryDto updateInventory(InventoryDto inventoryDto);

    void deleteInventory(UUID id);

}
