package com.aditya.inventory.services;


import com.aditya.inventory.dto.InventoryDto;
import com.aditya.inventory.models.InventoryEntity;
import com.aditya.inventory.repositories.InventoryRepository;
import com.aditya.product.models.ProductEntity;
import com.aditya.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {


    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, ModelMapper modelMapper) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public InventoryDto addInventory(InventoryDto inventoryDto) {
        ProductEntity product = productRepository.findBySkuCode(inventoryDto.getSkuCode()).orElseThrow(() -> new RuntimeException("Product with SKU " + inventoryDto.getSkuCode() + " not found"));

        InventoryEntity inventoryEntity = modelMapper.map(inventoryDto, InventoryEntity.class);
        inventoryEntity.setProduct(product);
        InventoryEntity savedInventory = inventoryRepository.save(inventoryEntity);
        InventoryDto savedInventoryDto = modelMapper.map(savedInventory, InventoryDto.class);
        savedInventoryDto.setSkuCode(product.getSkuCode());

        return savedInventoryDto;
    }

    @Override
    public InventoryDto updateInventory(InventoryDto inventoryDto) {

        InventoryEntity existingInventory = inventoryRepository.findByProduct_SkuCode(inventoryDto.getSkuCode()).orElseThrow(() -> new RuntimeException("No inventory found for SKU " + inventoryDto.getSkuCode()));

        existingInventory.setQuantity(inventoryDto.getQuantity());

        InventoryEntity updatedInventory = inventoryRepository.save(existingInventory);

        return modelMapper.map(updatedInventory, InventoryDto.class);
    }

    @Override
    public InventoryDto getInventory(String skuCode) {

        InventoryEntity inventoryEntity = inventoryRepository.findByProduct_SkuCode(skuCode).orElseThrow(() -> new RuntimeException("No inventory found for SKU " + skuCode));

        InventoryDto inventoryDto = modelMapper.map(inventoryEntity, InventoryDto.class);

        inventoryDto.setSkuCode(inventoryEntity.getProduct().getSkuCode());

        return inventoryDto;
    }

    @Override
    public Page<InventoryDto> getAllInventory(Pageable pageable) {
        Page<InventoryEntity> inventoryEntityPage = inventoryRepository.findAll(pageable);

        List<InventoryDto> dtoList = inventoryEntityPage.getContent().stream().map(inventoryEntity -> {
            InventoryDto inventoryDto = modelMapper.map(inventoryEntity, InventoryDto.class);
            inventoryDto.setSkuCode(inventoryEntity.getProduct().getSkuCode());
            return inventoryDto;
        }).toList();

        return new PageImpl<>(dtoList, pageable, inventoryEntityPage.getTotalElements());
    }

    @Override
    public void deleteInventory(String skuCode) {
        InventoryEntity inventoryEntity = inventoryRepository.findByProduct_SkuCode(skuCode).orElseThrow(() -> new RuntimeException("No inventory found for SKU " + skuCode));
        inventoryRepository.delete(inventoryEntity);

    }
}
