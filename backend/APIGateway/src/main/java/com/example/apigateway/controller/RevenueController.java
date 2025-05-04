package com.example.apigateway.controller;

import com.example.apigateway.service.KafkaPublisherService;
import com.example.common.model.RevenueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    @GetMapping("/{monthYear}")
    public ResponseEntity<Void> getRevenueForMonth(@PathVariable String monthYear) {
        RevenueRequest request = new RevenueRequest();
        request.setMonth(monthYear);
        kafkaPublisherService.requestRevenue(request);
        return ResponseEntity.accepted().build();
    }
}
