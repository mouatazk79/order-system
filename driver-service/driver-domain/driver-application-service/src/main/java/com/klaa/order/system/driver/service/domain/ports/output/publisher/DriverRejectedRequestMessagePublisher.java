package com.klaa.order.system.driver.service.domain.ports.output.publisher;

import com.klaa.order.system.driver.service.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

public interface DriverRejectedRequestMessagePublisher extends DomainEventPublisher<OrderDriverRejectedEvent> {
}
