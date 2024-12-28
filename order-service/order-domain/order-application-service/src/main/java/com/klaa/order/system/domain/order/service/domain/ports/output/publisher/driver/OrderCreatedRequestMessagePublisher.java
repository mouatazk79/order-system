package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.driver;


import com.klaa.order.system.domain.order.service.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCreatedRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
