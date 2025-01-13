package com.klaa.order.system.order.service.domain.outbox.model.payment;

import com.klaa.order.system.domain.valueobjects.OrderStatus;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Getter
@AllArgsConstructor
public class PaymentRequestOutboxMessage {
    private UUID id;
    private UUID sagaId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String type;
    private String payload;
    private SagaStatus sagaStatus;
    private OrderStatus orderStatus;
    private OutboxStatus outboxStatus;
    private int version;

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
