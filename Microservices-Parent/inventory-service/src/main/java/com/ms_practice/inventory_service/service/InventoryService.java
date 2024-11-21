package com.ms_practice.inventory_service.service;

import com.ms_practice.inventory_service.dto.InventoryResponse;
import com.ms_practice.inventory_service.model.Inventory;
import com.ms_practice.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Builder
//@RequiredArgsConstructor
public class InventoryService
{

    @Autowired
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    // @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode)
    {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity()>0)
                                .build()
                        ).toList();


    }

   public Inventory saveinventory(Inventory inventory)
   {
       return inventoryRepository.save(inventory);
   }
}
