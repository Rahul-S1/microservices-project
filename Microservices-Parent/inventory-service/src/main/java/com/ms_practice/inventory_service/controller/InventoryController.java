package com.ms_practice.inventory_service.controller;

import com.ms_practice.inventory_service.dto.InventoryResponse;
import com.ms_practice.inventory_service.model.Inventory;
import com.ms_practice.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("api/inventory")
public class InventoryController
{



    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode)
    {
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory addInventory(@RequestBody Inventory inventory)
    {
        return inventoryService.saveinventory(inventory);
    }

}
