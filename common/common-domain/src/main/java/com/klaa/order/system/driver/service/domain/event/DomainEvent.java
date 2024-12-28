package com.klaa.order.system.driver.service.domain.event;

public interface DomainEvent<T> {
    void fire();
}
