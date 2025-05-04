package com.example.apigateway.service;

import com.example.common.config.KafkaTopics;
import com.example.common.model.OrderStatusUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaWebSocketListener {

    @Autowired
    private WebSocketPushService webSocketPushService;

    @KafkaListener(topics = KafkaTopics.UPDATE_PRODUCT_STATUS, groupId = "gateway-websocket-group")
    public void onStatusUpdate(OrderStatusUpdate update) {
        webSocketPushService.sendOrderStatusUpdate(update);
    }
}
