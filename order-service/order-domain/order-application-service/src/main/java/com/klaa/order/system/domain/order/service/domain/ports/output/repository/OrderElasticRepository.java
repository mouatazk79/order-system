package com.klaa.order.system.domain.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.elastic.model.ElasticMessageStatus;
import com.klaa.order.system.domain.order.service.domain.elastic.model.OrderElasticMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderElasticRepository {
    Optional<OrderElasticMessage> save(OrderElasticMessage orderElasticMessage);
    Optional< List<OrderElasticMessage>> getAllOrderElasticMessageByStatus(ElasticMessageStatus... messageStatuses);
    void deleteOrderElasticMessageById(UUID id);
}
