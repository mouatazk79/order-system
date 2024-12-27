package com.klaa.order.system.domain.ports.output.publisher.reject;

import com.klaa.order.system.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

public interface OrderRejectMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {

}
