package com.klaa.order.system.domain.ports.output.publisher.payment;


import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
