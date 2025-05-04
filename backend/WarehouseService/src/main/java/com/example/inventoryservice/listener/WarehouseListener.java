package com.example.warehouse.listener;

import com.example.common.config.KafkaTopics;
import com.example.common.model.InventoryUpdate;
import com.example.common.model.MaterialRequest;
import com.example.warehouse.model.InventoryItem;
import com.example.warehouse.repository.InventoryRepository;
import com.example.warehouse.publisher.WarehousePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WarehouseListener {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private WarehousePublisher warehousePublisher;

    @KafkaListener(topics = KafkaTopics.REQUEST_MATERIALS, groupId = "warehouse-group")
    public void handleMaterialRequest(MaterialRequest request) {
        InventoryItem item = inventoryRepository.findByProductId(request.getProductId());
        if (item != null && item.getQuantity() >= request.getQuantity()) {
            item.setQuantity(item.getQuantity() - request.getQuantity());
            inventoryRepository.save(item);
            warehousePublisher.publishInventoryInfo(item);
            if (item.getQuantity() < 10) {
                warehousePublisher.publishPurchaseRequest(item);
            }
        }
    }

    @KafkaListener(topics = KafkaTopics.UPDATE_INVENTORY, groupId = "warehouse-group")
    public void handleInventoryUpdate(InventoryUpdate update) {
        InventoryItem item = inventoryRepository.findByProductId(update.getProductId());
        if (item == null) {
            item = new InventoryItem();
            item.setProductId(update.getProductId());
            item.setQuantity(update.getQuantity());
        } else {
            item.setQuantity(item.getQuantity() + update.getQuantity());
        }
        inventoryRepository.save(item);
        warehousePublisher.publishInventoryInfo(item);
    }
}
