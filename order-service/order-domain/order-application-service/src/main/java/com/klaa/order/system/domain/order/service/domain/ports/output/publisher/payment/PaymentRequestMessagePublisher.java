package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.payment;

import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {
    void publish(PaymentRequestOutboxMessage paymentRequestOutboxMessage,
                 BiConsumer<PaymentRequestOutboxMessage, OutboxStatus> outboxCallback);
}
