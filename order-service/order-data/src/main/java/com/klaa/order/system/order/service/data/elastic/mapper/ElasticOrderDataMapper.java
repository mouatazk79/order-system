package com.klaa.order.system.order.service.data.elastic.mapper;

import com.klaa.order.system.domain.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.order.service.data.elastic.entity.ElasticOrderEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ElasticOrderDataMapper {
    public ElasticOrderEntity orderElasticMessageToElasticOrderEntity(OrderElasticMessage orderElasticMessage) {
        return null;
    }

    public OrderElasticMessage elasticOrderEntityToOrderElasticMessage(ElasticOrderEntity save) {
        return null;
    }
}
