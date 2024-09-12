package com.aditya.inventory.services;

import com.aditya.common.exceptions.ResourceNotFoundException;
import com.aditya.common.utils.EntityDtoMapper;
import com.aditya.inventory.dto.InventoryDto;
import com.aditya.inventory.models.InventoryEntity;
import com.aditya.inventory.repositories.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {


    private final EntityDtoMapper entityDtoMapper;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    public InventoryServiceImpl(EntityDtoMapper entityDtoMapper, InventoryRepository inventoryRepository, ModelMapper modelMapper) {

        this.entityDtoMapper = entityDtoMapper;
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InventoryDto getInventory(String id) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("inventory not found!!", id));
        return entityDtoMapper.toDto(inventoryEntity, InventoryDto.class);
    }

    @Override
    public List<InventoryDto> getAllInventories() {
        List<InventoryEntity> inventoryEntityList = inventoryRepository.findAll();
        return inventoryEntityList.stream().map(inventory -> entityDtoMapper.toDto(inventory, InventoryDto.class)).toList();
    }

    @Override
    public InventoryDto addInventory(InventoryDto inventoryDto) {
        inventoryDto.setCreatedAt(new Date());
        InventoryEntity savedInventory = inventoryRepository.save(entityDtoMapper.toEntity(inventoryDto, InventoryEntity.class));
        return entityDtoMapper.toDto(savedInventory, InventoryDto.class);
    }

    @Override
    public InventoryDto updateInventory(InventoryDto inventoryDto) {
        String id = inventoryDto.getId();
        InventoryEntity inventoryEntity = inventoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Inventory not found!!", id)
                );
        inventoryDto.setUpdatedAt(new Date());
        modelMapper.map(inventoryDto, inventoryEntity);
        InventoryEntity savedInventoryEntity = inventoryRepository.save(inventoryEntity);
        return entityDtoMapper.toDto(savedInventoryEntity, InventoryDto.class);
    }


    @Override
    public void deleteInventory(String id) {
        InventoryEntity inventoryEntity = inventoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("inventory not found!!", id)
                );

        inventoryRepository.deleteById(id);


    }


}
