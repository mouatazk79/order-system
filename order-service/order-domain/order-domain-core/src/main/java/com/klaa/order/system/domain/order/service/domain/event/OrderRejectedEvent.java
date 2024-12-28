package com.klaa.order.system.domain.order.service.domain.event;

import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

import java.time.LocalDateTime;

public class OrderRejectedEvent extends OrderEvent{
    private final DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher;

    public OrderRejectedEvent(Order order, LocalDateTime localDateTime, DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher) {
        super(order, localDateTime);
        this.orderRejectedEventDomainEventPublisher = orderRejectedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderRejectedEventDomainEventPublisher.publish(this);

    }
}
