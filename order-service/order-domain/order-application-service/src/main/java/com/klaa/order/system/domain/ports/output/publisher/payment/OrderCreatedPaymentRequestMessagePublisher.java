package com.klaa.order.system.domain.ports.output.publisher.payment;


import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
