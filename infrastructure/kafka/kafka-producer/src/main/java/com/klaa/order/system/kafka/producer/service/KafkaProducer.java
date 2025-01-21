package com.klaa.order.system.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;

import java.io.Serializable;
import java.util.function.BiConsumer;

public interface KafkaProducer <K extends Serializable, V extends SpecificRecordBase> {
    void send(String topicName, K key, V payload, BiConsumer<SendResult<K, V>, Throwable> callback);
}
