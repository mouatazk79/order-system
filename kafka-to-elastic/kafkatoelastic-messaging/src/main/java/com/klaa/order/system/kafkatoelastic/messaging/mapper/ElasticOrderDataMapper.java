package com.klaa.order.system.kafkatoelastic.messaging.mapper;

import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticOrderDataMapper {
    public List<OrderIndexModel> orderElasticMessageAvroModelToOrderIndexModel(OrderElasticMessageAvroModel message) {
        return null;
    }
}
