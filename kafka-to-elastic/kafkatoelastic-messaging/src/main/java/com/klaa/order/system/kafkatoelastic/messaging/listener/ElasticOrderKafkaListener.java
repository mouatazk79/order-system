package com.klaa.order.system.kafkatoelastic.messaging.listener;


import com.klaa.order.system.elastic.indexclient.service.ElasticIndexClient;
import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import com.klaa.order.system.kafka.consumer.consumer.KafkaConsumer;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
import com.klaa.order.system.kafkatoelastic.messaging.mapper.ElasticOrderDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class  ElasticOrderKafkaListener implements KafkaConsumer<OrderElasticMessageAvroModel> {
    private final ElasticIndexClient<OrderIndexModel> elasticIndexClient;
    private final ElasticOrderDataMapper elasticOrderDataMapper;

    public ElasticOrderKafkaListener(ElasticIndexClient<OrderIndexModel> elasticIndexClient, ElasticOrderDataMapper elasticOrderDataMapper) {
        this.elasticIndexClient = elasticIndexClient;
        this.elasticOrderDataMapper = elasticOrderDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.kafka-to-elastic-consumer-group-id}",
            topics = "${kafka-to-elastic-service.kafka-to-elastic-topic-name}")
    public void receive(@Payload List<OrderElasticMessageAvroModel> messages, @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,@Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,@Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        messages.forEach(message -> {
            List<String> ids= elasticIndexClient.save(elasticOrderDataMapper.orderElasticMessageAvroModelToOrderIndexModel(message));
            log.info("saving {} OrderIndexModels",ids.size());
        });

    }
}
