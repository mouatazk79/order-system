package com.klaa.order.system.kafkatoelastic.messaging.listener;


import com.klaa.order.system.elastic.indexclient.service.ElasticIndexClient;
import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import com.klaa.order.system.kafka.consumer.consumer.KafkaConsumer;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
import com.klaa.order.system.kafkatoelastic.messaging.mapper.ElasticOrderDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ElasticOrderKafkaListener implements KafkaConsumer<OrderElasticMessageAvroModel> {
    private final ElasticIndexClient<OrderIndexModel> elasticIndexClient;
    private final ElasticOrderDataMapper elasticOrderDataMapper;
    @Override
    @KafkaListener
    public void receive(@Payload List<OrderElasticMessageAvroModel> messages, @Header(KafkaHeaders.KEY) List<String> keys,@Header(KafkaHeaders.PARTITION) List<Integer> partitions,@Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        messages.forEach(message -> {
            elasticIndexClient.save(elasticOrderDataMapper.orderElasticMessageAvroModelToOrderIndexModel(message));
        });



    }
}
