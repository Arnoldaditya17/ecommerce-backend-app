package com.aditya.inventory.controllers;

import com.aditya.inventory.dto.CreatedInventoryDto;
import com.aditya.inventory.dto.InventoryDto;
import com.aditya.inventory.services.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @PostMapping
    public ResponseEntity<CreatedInventoryDto> createInventory(@RequestBody CreatedInventoryDto createdInventoryDto) {
        CreatedInventoryDto savedInventory=inventoryService.addInventory(createdInventoryDto);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CreatedInventoryDto>> getAllInventory(Pageable pageable) {
        Page<CreatedInventoryDto> inventories = inventoryService.getAllInventory(pageable);
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable String skuCode) {
        return new ResponseEntity<>(inventoryService.getInventory(skuCode), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<InventoryDto> updateInventory(@RequestBody InventoryDto inventoryDto) {
        InventoryDto updatedInventory = inventoryService.updateInventory(inventoryDto);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    @DeleteMapping("/{skuCode}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String skuCode) {
        inventoryService.deleteInventory(skuCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
