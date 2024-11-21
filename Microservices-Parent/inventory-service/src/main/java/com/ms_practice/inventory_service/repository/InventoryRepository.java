package com.ms_practice.inventory_service.repository;

import com.ms_practice.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    //Optional<Inventory> findBySkuCode(String skucode);


    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
