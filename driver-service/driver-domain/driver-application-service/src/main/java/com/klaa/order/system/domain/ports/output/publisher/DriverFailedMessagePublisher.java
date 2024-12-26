package com.klaa.order.system.domain.ports.output.publisher;

import com.klaa.order.system.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface DriverFailedMessagePublisher extends DomainEventPublisher<OrderDriverFailedEvent> {
}
