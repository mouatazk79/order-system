package com.klaa.order.system.order.service.data.outbox.payment.mapper;

import com.klaa.order.system.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.order.service.data.outbox.payment.entity.PaymentOutboxMessageEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentOutboxDataMapper {
    public  PaymentRequestOutboxMessage paymentOutboxMessageEntityToPaymentRequestOutboxMessage(PaymentOutboxMessageEntity paymentOutboxMessageEntity) {
        return PaymentRequestOutboxMessage.builder()
                .id(paymentOutboxMessageEntity.getId())
                .sagaId(paymentOutboxMessageEntity.getSagaId())
                .createdAt(paymentOutboxMessageEntity.getCreatedAt())
                .type(paymentOutboxMessageEntity.getType())
                .payload(paymentOutboxMessageEntity.getPayload())
                .sagaStatus(paymentOutboxMessageEntity.getSagaStatus())
                .orderStatus(paymentOutboxMessageEntity.getOrderStatus())
                .outboxStatus(paymentOutboxMessageEntity.getOutboxStatus())
                .version(paymentOutboxMessageEntity.getVersion())
                .build();
    }

    public PaymentOutboxMessageEntity paymentRequestOutboxMessageToPaymentOutboxMessageEntity(PaymentRequestOutboxMessage paymentRequestOutboxMessage) {
        return PaymentOutboxMessageEntity.builder()
                .id(paymentRequestOutboxMessage.getId())
                .sagaId(paymentRequestOutboxMessage.getSagaId())
                .createdAt(paymentRequestOutboxMessage.getCreatedAt())
                .type(paymentRequestOutboxMessage.getType())
                .payload(paymentRequestOutboxMessage.getPayload())
                .sagaStatus(paymentRequestOutboxMessage.getSagaStatus())
                .orderStatus(paymentRequestOutboxMessage.getOrderStatus())
                .outboxStatus(paymentRequestOutboxMessage.getOutboxStatus())
                .version(paymentRequestOutboxMessage.getVersion())
                .build();
    }
}
