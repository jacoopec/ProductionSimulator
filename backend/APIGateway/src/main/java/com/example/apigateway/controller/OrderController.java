package com.example.apigateway.controller;

import com.example.apigateway.service.KafkaPublisherService;
import com.example.common.model.NewOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/current-orders")
public class OrderController {

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    @PostMapping("/neworder")
    public ResponseEntity<Void> createOrder(@RequestBody NewOrderRequest request) {
        kafkaPublisherService.publishNewOrder(request);
        return ResponseEntity.accepted().build();
    }

    // WebSocket is handled separately through WebSocketPushService
}
