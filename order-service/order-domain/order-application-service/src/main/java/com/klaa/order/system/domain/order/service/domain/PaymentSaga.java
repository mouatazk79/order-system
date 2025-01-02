package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.message.PaymentResponse;
import com.klaa.order.system.domain.event.EmptyEvent;
import com.klaa.order.system.domain.order.service.domain.event.OrderCancelledEvent;
import com.klaa.order.system.saga.SagaStep;

public class PaymentSaga implements SagaStep<PaymentResponse> {
    @Override
    public void process(PaymentResponse data) {

    }

    @Override
    public void rollBack(PaymentResponse data) {

    }
}
