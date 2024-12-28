package com.klaa.order.system.driver.service.domain.event.publisher;

import com.klaa.order.system.driver.service.domain.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent>{
    void publish(T domainEvent);

}
