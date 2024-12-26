package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.dto.message.PaymentResponse;
import com.klaa.order.system.domain.event.EmptyEvent;
import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.saga.SagaStep;

public class PaymentSaga implements SagaStep<PaymentResponse, EmptyEvent, OrderCancelledEvent> {
    @Override
    public EmptyEvent process(PaymentResponse data) {
        return null;
    }

    @Override
    public OrderCancelledEvent rollBack(PaymentResponse data) {
        return null;
    }
}
