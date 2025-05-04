package com.example.apigateway.controller;

import com.example.apigateway.service.KafkaPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    @GetMapping("/materials")
    public ResponseEntity<Void> requestMaterialsInfo() {
        kafkaPublisherService.requestInventory("materials");
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/finished-products")
    public ResponseEntity<Void> requestProductsInfo() {
        kafkaPublisherService.requestInventory("finished-products");
        return ResponseEntity.accepted().build();
    }
}
