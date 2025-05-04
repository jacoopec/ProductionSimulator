package com.example.product.listener;

import com.example.common.config.KafkaTopics;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestListener {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = KafkaTopics.PRODUCT_INFO_REQUEST, groupId = "product-service-group")
    public void handleProductInfoRequest(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            kafkaTemplate.send(KafkaTopics.PRODUCT_INFO, product);
        }
    }

    @KafkaListener(topics = KafkaTopics.NEW_PRODUCT, groupId = "product-service-group")
    public void handleNewProduct(Product product) {
        productRepository.save(product);
        kafkaTemplate.send(KafkaTopics.PRODUCT_INFO, product);
    }
}