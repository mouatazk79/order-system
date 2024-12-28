package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.payment;


import com.klaa.order.system.domain.order.service.domain.event.OrderCreatedEvent;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
