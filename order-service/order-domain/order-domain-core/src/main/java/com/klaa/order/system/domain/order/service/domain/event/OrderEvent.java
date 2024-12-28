package com.klaa.order.system.domain.order.service.domain.event;

import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.driver.service.domain.event.DomainEvent;

import java.time.LocalDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
private final Order order;
private final LocalDateTime localDateTime;

    public OrderEvent(Order order, LocalDateTime localDateTime) {
        this.order = order;
        this.localDateTime = localDateTime;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
