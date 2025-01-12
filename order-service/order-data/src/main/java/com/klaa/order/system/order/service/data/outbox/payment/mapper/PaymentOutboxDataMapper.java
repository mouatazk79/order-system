package com.klaa.order.system.order.service.data.outbox.payment.mapper;

import com.klaa.order.system.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.order.service.data.outbox.payment.entity.PaymentOutboxMessageEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentOutboxDataMapper {
    public  PaymentRequestOutboxMessage paymentOutboxMessageEntityToPaymentRequestOutboxMessage(PaymentOutboxMessageEntity save) {
        return null;
    }

    public PaymentOutboxMessageEntity paymentRequestOutboxMessageToPaymentOutboxMessageEntity(PaymentRequestOutboxMessage paymentRequestOutboxMessage) {
        return null;
    }
}
