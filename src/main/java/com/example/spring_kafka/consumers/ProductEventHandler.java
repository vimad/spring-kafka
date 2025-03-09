package com.example.spring_kafka.consumers;

import com.example.spring_kafka.model.Product;
import com.example.spring_kafka.model.ProductEventEntity;
import com.example.spring_kafka.repository.ProductEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;

@Component
@KafkaListener(topicPattern = "product-created-topic")
@Slf4j
public class ProductEventHandler {

    private final ProductEventRepository productEventRepository;

    public ProductEventHandler(ProductEventRepository productEventRepository) {
        this.productEventRepository = productEventRepository;
    }

    @KafkaHandler
    public void handle(@Payload Product product,
                       @Header("messageId") String messageId,
                       @Header(RECEIVED_KEY) String messageKey) {
        log.info("ProductEventHandler received event {}", product);
        ProductEventEntity byMessageId = productEventRepository.findByMessageId(messageId);
        if (byMessageId != null) {
            log.info("Message already processed");
            return;
        }

        try {
            productEventRepository.save(new ProductEventEntity(messageKey, messageId));
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
        }
    }

}
