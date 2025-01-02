package com.klaa.order.system.domain.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOutboxRepository {
    Optional<PaymentRequestOutboxMessage> save(PaymentRequestOutboxMessage paymentRequestOutboxMessage);
    void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus);
    Optional<List<PaymentRequestOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus);
    Optional<PaymentRequestOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type, UUID sagaId, SagaStatus... sagaStatus);


}
