package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.message.PaymentResponse;
import com.klaa.order.system.domain.ports.input.listener.payment.PaymentResponseMessageListener;

public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}
