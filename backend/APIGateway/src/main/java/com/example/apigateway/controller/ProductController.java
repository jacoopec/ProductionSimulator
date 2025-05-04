package com.example.apigateway.controller;

import com.example.apigateway.service.KafkaPublisherService;
import com.example.common.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    @PostMapping("/new")
    public ResponseEntity<Void> addNewProduct(@RequestBody ProductInfo product) {
        kafkaPublisherService.publishNewProduct(product);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> requestProductInfo(@PathVariable String id) {
        kafkaPublisherService.requestProductInfo(id);
        return ResponseEntity.accepted().build();
    }
}
