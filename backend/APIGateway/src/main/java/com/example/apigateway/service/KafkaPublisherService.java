package com.example.apigateway.service;

import com.example.common.config.KafkaTopics;
import com.example.common.model.NewOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishNewOrder(NewOrderRequest order) {
        kafkaTemplate.send(KafkaTopics.NEW_ORDER_TOPIC, order);
    }

    public void publishNewProduct(ProductInfo product) {
        kafkaTemplate.send(KafkaTopics.NEW_PRODUCT, product);
    }

    public void requestProductInfo(String productId) {
        kafkaTemplate.send(KafkaTopics.PRODUCT_INFO_REQUEST, productId);
    }

}
