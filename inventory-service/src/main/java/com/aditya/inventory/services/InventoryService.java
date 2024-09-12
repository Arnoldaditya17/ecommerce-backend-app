package com.aditya.inventory.services;


import com.aditya.inventory.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    InventoryDto getInventory(String id);

    List<InventoryDto> getAllInventories();

    InventoryDto addInventory(InventoryDto inventoryDto);

    InventoryDto updateInventory(InventoryDto inventoryDto);

    void deleteInventory(String id);

}
