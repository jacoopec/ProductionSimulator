package com.example.management.repository;

import com.example.management.model.Revenue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RevenueRepository extends MongoRepository<Revenue, String> {
    Revenue findByMonth(String month);
}
