package com.example.production.listener;

import com.example.common.config.KafkaTopics;
import com.example.common.model.NewOrderRequest;
import com.example.common.model.OrderStatusUpdate;
import com.example.production.publisher.ProductionPublisher;
import com.example.production.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductionListener {

    @Autowired
    private ProductionService productionService;

    @KafkaListener(topics = KafkaTopics.NEW_ORDER_TOPIC, groupId = "production-group")
    public void handleNewOrder(NewOrderRequest request) {
        productionService.processNewOrder(request);
    }
}