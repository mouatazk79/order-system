package com.klaa.order.system.driver.service.domain.ports.output.publisher;

import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

public interface DriverApprovedRequestMessagePublisher extends DomainEventPublisher<OrderDriverApprovedEvent> {
}
