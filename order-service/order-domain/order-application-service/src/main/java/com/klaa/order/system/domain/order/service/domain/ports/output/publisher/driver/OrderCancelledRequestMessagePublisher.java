package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.driver;


import com.klaa.order.system.domain.order.service.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelledRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
