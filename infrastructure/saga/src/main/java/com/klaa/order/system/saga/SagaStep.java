package com.klaa.order.system.saga;

import com.klaa.order.system.domain.event.DomainEvent;

public interface SagaStep<T,S extends DomainEvent,U extends DomainEvent>{
    S process(T data);
    U rollBack(T data);
}
