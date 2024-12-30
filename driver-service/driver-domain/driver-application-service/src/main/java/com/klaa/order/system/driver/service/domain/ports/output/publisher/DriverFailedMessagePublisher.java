package com.klaa.order.system.driver.service.domain.ports.output.publisher;

import com.klaa.order.system.driver.service.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface DriverFailedMessagePublisher extends DomainEventPublisher<OrderDriverFailedEvent> {
}
