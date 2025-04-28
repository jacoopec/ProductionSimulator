package com.example.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final KafkaTemplate<String, InventoryUpdateEvent> kafkaTemplate;
    private static final String UPDATE_INVENTORY_TOPIC = "updateInventory";

    private double budget = 20000.0;

    // Simulated price list for purchasing raw materials
    private static final Map<String, Double> productPrices = new HashMap<>();

    static {
        productPrices.put("Steel", 50.0);
        productPrices.put("Plastic", 30.0);
        productPrices.put("Wood", 40.0);
        // Add more as needed
    }

    @KafkaListener(topics = "requiredPurchaseTopic", groupId = "management-group")
    public void handlePurchaseRequest(String productName) {
        double cost = productPrices.getOrDefault(productName, 50.0) * 100; // Buying 100 units
        budget -= cost;
        System.out.println("Purchased " + productName + " for $" + cost + ". New budget: $" + budget);

        // Publish update to inventory
        InventoryUpdateEvent updateEvent = new InventoryUpdateEvent(productName, 100);
        kafkaTemplate.send(UPDATE_INVENTORY_TOPIC, updateEvent);
    }
}
