package com.klaa.order.system.domain;

import com.klaa.order.system.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface DriverDomainService {
    OrderDriverApprovalEvent validateAndApproveOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverFailedEvent> orderDriverFailedEventDomainEventPublisher);
    OrderDriverApprovalEvent validateAndRejectOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverRejectedEvent> orderDriverRejectedEventDomainEventPublisher);
}
