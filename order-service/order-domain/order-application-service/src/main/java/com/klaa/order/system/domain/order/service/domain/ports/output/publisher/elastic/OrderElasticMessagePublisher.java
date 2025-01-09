package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.elastic;

import com.klaa.order.system.domain.valueobjects.ElasticMessageStatus;
import com.klaa.order.system.domain.order.service.domain.elastic.model.OrderElasticMessage;


import java.util.function.BiConsumer;

public interface OrderElasticMessagePublisher {
    void publish(OrderElasticMessage orderElasticMessage,
                 BiConsumer<OrderElasticMessage, ElasticMessageStatus> elasticCallback);
}
