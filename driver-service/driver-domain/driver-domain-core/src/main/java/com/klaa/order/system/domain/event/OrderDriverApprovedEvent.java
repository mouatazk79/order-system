package com.klaa.order.system.domain.event;

import com.klaa.order.system.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDriverApprovedEvent extends OrderDriverApprovalEvent{
    private final DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher;

    protected OrderDriverApprovedEvent(OrderApproval orderApproval, List<String> failureMessages, LocalDateTime localDateTime, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher) {
        super(orderApproval, failureMessages, localDateTime);
        this.orderDriverApprovedEventDomainEventPublisher = orderDriverApprovedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderDriverApprovedEventDomainEventPublisher.publish(this);

    }
}
