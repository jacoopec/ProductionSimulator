package com.example.ordersservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Product {
    @Id
    private String id;
    private String prodType;
    private String orderStatus;
    private String deliveryDate;
    private String customer;
    private int quantity;
}
