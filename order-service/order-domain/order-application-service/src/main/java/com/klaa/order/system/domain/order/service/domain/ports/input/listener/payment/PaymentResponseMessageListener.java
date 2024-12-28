package com.klaa.order.system.domain.order.service.domain.ports.input.listener.payment;

import com.klaa.order.system.domain.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
