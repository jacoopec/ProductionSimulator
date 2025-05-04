package com.example.apigateway.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketPushService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketPushService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOrderStatusUpdate(Object statusUpdate) {
        messagingTemplate.convertAndSend("/topic/order-status", statusUpdate);
    }
}