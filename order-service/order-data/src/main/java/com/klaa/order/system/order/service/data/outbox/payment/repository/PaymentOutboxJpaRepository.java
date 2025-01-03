package com.klaa.order.system.order.service.data.outbox.payment.repository;

import com.klaa.order.system.order.service.data.outbox.payment.entity.PaymentOutboxMessageEntity;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxMessageEntity, UUID> {

    Optional<List<PaymentOutboxMessageEntity>> findByTypeAndOutboxStatusAndSagaStatusIn(String type,
                                                                                        OutboxStatus outboxStatus,
                                                                                        List<SagaStatus> sagaStatus);

    Optional<PaymentOutboxMessageEntity> findByTypeAndSagaIdAndSagaStatusIn(String type,
                                                                     UUID sagaId,
                                                                     List<SagaStatus> sagaStatus);

    void deleteByTypeAndOutboxStatusAndSagaStatusIn(String type,
                                                    OutboxStatus outboxStatus,
                                                    List<SagaStatus> sagaStatus);
}
