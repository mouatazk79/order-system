package com.klaa.order.system.saga;

import com.klaa.order.system.domain.event.DomainEvent;

public interface SagaStep<T>{
    void process(T data);
    void rollBack(T data);
}
