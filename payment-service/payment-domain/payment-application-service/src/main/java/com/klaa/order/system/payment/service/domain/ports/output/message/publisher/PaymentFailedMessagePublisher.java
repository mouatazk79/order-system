package com.klaa.order.system.payment.service.domain.ports.output.message.publisher;


import com.klaa.order.service.payment.service.domain.event.PaymentFailedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface PaymentFailedMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {
}
