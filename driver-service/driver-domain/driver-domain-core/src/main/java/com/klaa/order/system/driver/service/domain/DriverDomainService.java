package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.driver.service.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.driver.service.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface DriverDomainService {
    OrderDriverApprovalEvent validateAndApproveOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverFailedEvent> orderDriverFailedEventDomainEventPublisher);
    OrderDriverApprovalEvent validateAndRejectOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverRejectedEvent> orderDriverRejectedEventDomainEventPublisher);
}
