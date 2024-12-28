package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.payment;


import com.klaa.order.system.domain.order.service.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
