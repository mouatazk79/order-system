package com.klaa.order.system.domain.ports.output.publisher;

import com.klaa.order.system.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface DriverApprovedRequestMessagePublisher extends DomainEventPublisher<OrderDriverApprovalEvent> {
}
