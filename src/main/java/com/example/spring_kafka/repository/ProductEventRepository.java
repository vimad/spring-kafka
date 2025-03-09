package com.example.spring_kafka.repository;

import com.example.spring_kafka.model.ProductEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEventRepository extends JpaRepository<ProductEventEntity, Long> {

    ProductEventEntity findByMessageId(String messageId);
}
