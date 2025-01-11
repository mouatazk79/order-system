package com.klaa.order.system.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.valueobjects.ElasticMessageStatus;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderElasticRepository {
    Optional<OrderElasticMessage> save(OrderElasticMessage orderElasticMessage);
    Optional< List<OrderElasticMessage>> getAllOrderElasticMessageByStatus(ElasticMessageStatus... messageStatuses);
    void deleteOrderElasticMessageById(UUID id);
}
