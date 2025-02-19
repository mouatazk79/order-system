package com.klaa.order.system.order.service.data.elastic.mapper;

import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.order.service.data.elastic.entity.ElasticOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class ElasticOrderDataMapper {
    public ElasticOrderEntity orderElasticMessageToElasticOrderEntity(OrderElasticMessage orderElasticMessage) {
        return ElasticOrderEntity.builder()
                .id(orderElasticMessage.getId())
                .outboxStatus(orderElasticMessage.getOutboxStatus())
                .payload(orderElasticMessage.getPayload())
                .build();
    }

    public OrderElasticMessage elasticOrderEntityToOrderElasticMessage(ElasticOrderEntity elasticOrderEntity) {
        return OrderElasticMessage.builder()
                .id(elasticOrderEntity.getId())
                .outboxStatus(elasticOrderEntity.getOutboxStatus())
                .payload(elasticOrderEntity.getPayload())
                .build();
    }
}
