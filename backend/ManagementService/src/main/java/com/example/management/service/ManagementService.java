package com.example.management.service;

import com.example.common.config.KafkaTopics;
import com.example.common.model.InventoryUpdate;
import com.example.common.model.PurchaseRequest;
import com.example.common.model.RevenueRequest;
import com.example.management.model.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ManagementService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void processPurchase(PurchaseRequest request) {
        InventoryUpdate update = new InventoryUpdate();
        update.setProductId(request.getProductId());
        update.setQuantity(request.getQuantity());
        kafkaTemplate.send(KafkaTopics.UPDATE_INVENTORY, update);
    }

    public void calculateRevenue(RevenueRequest request) {
        // Mocked revenue value for the demo
        Revenue revenue = new Revenue();
        revenue.setMonth(request.getMonth());
        revenue.setAmount(new Random().nextDouble() * 10000);
        kafkaTemplate.send(KafkaTopics.REVENUE_RESPONSE, revenue);
    }
}
