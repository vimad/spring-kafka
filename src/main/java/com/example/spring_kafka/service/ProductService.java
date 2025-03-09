package com.example.spring_kafka.service;

import com.example.spring_kafka.model.Product;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProductService {

    private final KafkaTemplate<String, Product> kafkaTemplate;

    public ProductService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String createProduct(Product product) {
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        sendSynchronously(product, productId);
        return productId;
    }

    @SneakyThrows
    private void sendSynchronously(Product product, String productId) {
//        SendResult<String, Product> result = kafkaTemplate.send("product-created-topic", productId, product).get();

        ProducerRecord<String, Product> record = new ProducerRecord<>("product-created-topic", productId, product);
//        record.headers().add("messageId", UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        record.headers().add("messageId", "123".getBytes(StandardCharsets.UTF_8));
        SendResult<String, Product> result = kafkaTemplate.send(record).get();

        log.info("Partition - {}", result.getRecordMetadata().partition());
        log.info("Topic - {}", result.getRecordMetadata().topic());
        log.info("Offset - {}", result.getRecordMetadata().offset());
    }

    private void sendAsynchronously(Product product, String productId) {
        CompletableFuture<SendResult<String, Product>> future = kafkaTemplate.send("product-created-topic", productId, product);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send message {}", exception.getMessage());
            } else {
                log.info("Successfully sent message {}", result.getRecordMetadata());
            }
        });

//        future.join(); // if need to wait
    }
}
