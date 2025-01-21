package com.klaa.order.system.order.service.domain.ports.output.publisher.elastic;

import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.outbox.OutboxStatus;


import java.util.function.BiConsumer;

public interface OrderElasticMessagePublisher {
    void publish(OrderElasticMessage orderElasticMessage,
                 BiConsumer<OrderElasticMessage, OutboxStatus> elasticCallback);
}
