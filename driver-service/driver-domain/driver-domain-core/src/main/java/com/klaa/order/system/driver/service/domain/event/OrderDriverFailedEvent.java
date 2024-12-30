package com.klaa.order.system.driver.service.domain.event;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDriverFailedEvent extends OrderDriverApprovalEvent  {
    private final DomainEventPublisher<OrderDriverFailedEvent> orderDriverFailedEventDomainEventPublisher;

    public OrderDriverFailedEvent(OrderApproval orderApproval, List<String> failureMessages, LocalDateTime localDateTime, DomainEventPublisher<OrderDriverFailedEvent> orderDriverFailedEventDomainEventPublisher) {
        super(orderApproval, failureMessages, localDateTime);
        this.orderDriverFailedEventDomainEventPublisher = orderDriverFailedEventDomainEventPublisher;
    }


    @Override
    public void fire() {
        orderDriverFailedEventDomainEventPublisher.publish(this);

    }
}
