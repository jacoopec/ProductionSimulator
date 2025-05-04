package com.example.management.listener;

import com.example.common.config.KafkaTopics;
import com.example.common.model.PurchaseRequest;
import com.example.common.model.RevenueRequest;
import com.example.management.model.Revenue;
import com.example.management.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ManagementListener {

    @Autowired
    private ManagementService managementService;

    @KafkaListener(topics = KafkaTopics.PURCHASE_REQUEST, groupId = "management-group")
    public void handlePurchaseRequest(PurchaseRequest request) {
        managementService.processPurchase(request);
    }

    @KafkaListener(topics = KafkaTopics.REVENUE_REQUEST, groupId = "management-group")
    public void handleRevenueRequest(RevenueRequest request) {
        managementService.calculateRevenue(request);
    }
}