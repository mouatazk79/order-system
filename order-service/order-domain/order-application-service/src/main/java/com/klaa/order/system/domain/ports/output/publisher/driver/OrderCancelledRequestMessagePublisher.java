package com.klaa.order.system.domain.ports.output.publisher.driver;


import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelledRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
