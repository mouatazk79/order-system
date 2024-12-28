package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.reject;

import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

public interface OrderRejectMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {

}
