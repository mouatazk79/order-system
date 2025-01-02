package com.klaa.order.system.domain.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverOutboxRepository {
    Optional<DriverRequestOutboxMessage> save(DriverRequestOutboxMessage driverRequestOutboxMessage);
    Optional<List<DriverRequestOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String type,OutboxStatus outboxStatus, SagaStatus... sagaStatus);
    Optional<DriverRequestOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type, UUID sagaId, SagaStatus... sagaStatus);
    void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus);
}
