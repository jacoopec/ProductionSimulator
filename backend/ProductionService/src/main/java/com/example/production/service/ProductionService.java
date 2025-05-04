package com.example.production.service;

import com.example.common.config.KafkaTopics;
import com.example.common.model.NewOrderRequest;
import com.example.common.model.OrderStatusUpdate;
import com.example.common.model.MaterialRequest;
import com.example.production.publisher.ProductionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductionService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ProductionPublisher productionPublisher;

    public void processNewOrder(NewOrderRequest request) {
        // Request material info for the product
        kafkaTemplate.send(KafkaTopics.PRODUCT_INFO_REQUEST, request.getProductId());

        // Mocked delay simulation, actual material info response handling not shown
        // here
        // Would continue processing after retrieving required materials from
        // product-service

        // For demo, directly simulate sending a request to warehouse
        MaterialRequest matRequest = new MaterialRequest();
        matRequest.setProductId(request.getProductId());
        matRequest.setQuantity(request.getQuantity());
        kafkaTemplate.send(KafkaTopics.REQUEST_MATERIALS, matRequest);

        // Simulate status update
        OrderStatusUpdate statusUpdate = new OrderStatusUpdate();
        statusUpdate.setOrderId(request.getOrderId());
        statusUpdate.setStatus("Processing started");

        kafkaTemplate.send(KafkaTopics.UPDATE_PRODUCT_STATUS, statusUpdate);
    }
}