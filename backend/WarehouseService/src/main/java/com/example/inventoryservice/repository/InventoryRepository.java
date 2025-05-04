package com.example.warehouse.repository;

import com.example.warehouse.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<InventoryItem, String> {
    InventoryItem findByProductId(String productId);
}