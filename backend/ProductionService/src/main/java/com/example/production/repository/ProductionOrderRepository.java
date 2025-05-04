package com.example.production.repository;

import com.example.production.model.ProductionOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductionOrderRepository extends MongoRepository<ProductionOrder, String> {
    ProductionOrder findByOrderId(String orderId);
}
