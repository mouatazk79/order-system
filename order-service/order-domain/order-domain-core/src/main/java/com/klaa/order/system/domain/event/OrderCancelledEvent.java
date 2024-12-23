package com.klaa.order.system.domain.event;

import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.LocalDateTime;

public class OrderCancelledEvent extends OrderEvent{
    private final DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher;

    public OrderCancelledEvent(Order order, LocalDateTime localDateTime, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher) {
        super(order, localDateTime);
        this.orderCancelledEventDomainEventPublisher = orderCancelledEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderCancelledEventDomainEventPublisher.publish(this);

    }
}
