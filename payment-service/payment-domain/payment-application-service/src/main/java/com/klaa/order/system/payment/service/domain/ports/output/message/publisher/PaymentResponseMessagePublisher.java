package com.klaa.order.system.payment.service.domain.ports.output.message.publisher;

import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.payment.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface PaymentResponseMessagePublisher {
    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
