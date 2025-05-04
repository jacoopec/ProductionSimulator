package com.example.ordersservice.service;

import com.example.ordersservice.model.Order;
import com.example.ordersservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    private static final String NEW_ORDER_TOPIC = "newOrderTopic";

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send(NEW_ORDER_TOPIC, savedOrder);
        return savedOrder;
    }
}