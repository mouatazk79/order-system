package com.klaa.order.system.domain.ports.output.publisher;

import com.klaa.order.system.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface DriverRejectedRequestMessagePublisher extends DomainEventPublisher<OrderDriverRejectedEvent> {
}
