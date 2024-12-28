package com.klaa.order.system.domain.order.service.domain.event;

import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

import java.time.LocalDateTime;

public class OrderCreatedEvent extends OrderEvent{
    private final DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher;

    public OrderCreatedEvent(Order order, LocalDateTime localDateTime, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher) {
        super(order, localDateTime);
        this.orderCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderCreatedEventDomainEventPublisher.publish(this);

    }
}