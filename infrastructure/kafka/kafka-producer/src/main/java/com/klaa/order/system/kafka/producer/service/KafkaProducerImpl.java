package com.klaa.order.system.kafka.producer.service;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {
    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void send(String topicName, K key, V payload) {
        log.info("Sending payload='{}' to topic='{}'", payload, topicName);
        CompletableFuture<SendResult<K, V>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, payload);
        kafkaResultFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                log.debug("Received new payload. Topic: {}",topicName);
            } else {
                log.error("Error sending message", ex);
            }
        });
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }


}