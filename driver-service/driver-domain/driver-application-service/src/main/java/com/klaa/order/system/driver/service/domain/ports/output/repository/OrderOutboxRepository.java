package com.klaa.order.system.driver.service.domain.ports.output.repository;

import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderOutboxRepository {

    OrderOutboxMessage save(OrderOutboxMessage orderOutboxMessage);

    Optional<List<OrderOutboxMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

    Optional<OrderOutboxMessage> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId,
                                                                    OutboxStatus outboxStatus);

    void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

}
