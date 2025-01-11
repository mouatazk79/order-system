package com.klaa.order.system.order.service.domain;

import com.klaa.order.system.order.service.domain.dto.message.PaymentResponse;
import com.klaa.order.system.order.service.domain.ports.input.listener.payment.PaymentResponseMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    private final PaymentSaga paymentSaga;
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        paymentSaga.process(paymentResponse);
        log.info("Payment approved the order for order id: {}", paymentResponse.getOrderId());
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        paymentSaga.rollBack(paymentResponse);
        log.info("Payment with id: {} rejected the order for order id: {}", paymentResponse.getPaymentId(),paymentResponse.getOrderId());

    }
}
