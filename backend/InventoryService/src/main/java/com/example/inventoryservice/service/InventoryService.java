package com.example.inventoryservice.service;

import com.example.inventoryservice.model.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String REQUIRED_PURCHASE_TOPIC = "requiredPurchaseTopic";

    public List<InventoryItem> getInventory() {
        return inventoryRepository.findAll();
    }

    @KafkaListener(topics = "newOrderTopic", groupId = "inventory-group")
    public void handleNewOrder(OrderEvent orderEvent) {
        // Mocked simple handling: subtract raw materials and add finished product
        // (Real production would match raw materials from orderEvent)
        System.out.println("Handling new order: " + orderEvent);

        // Here we would subtract raw materials and check for low stock

        // If any raw material is below minQuantityRequired, publish to
        // requiredPurchaseTopic
        Optional<InventoryItem> item = inventoryRepository.findById("someId"); // Replace with real logic
        item.ifPresent(i -> {
            if (i.getQuantity() < i.getMinQuantityRequired()) {
                kafkaTemplate.send(REQUIRED_PURCHASE_TOPIC, i.getName());
            }
        });
    }
}