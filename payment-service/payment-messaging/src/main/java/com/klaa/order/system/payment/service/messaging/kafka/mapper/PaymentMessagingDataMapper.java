package com.klaa.order.system.payment.service.messaging.kafka.mapper;



import com.klaa.order.system.domain.valueobjects.PaymentOrderStatus;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentResponseAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentStatus;
import com.klaa.order.system.payment.service.domain.dto.PaymentRequest;
import com.klaa.order.system.payment.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {
    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId().toString())
                .sagaId(paymentRequestAvroModel.getSagaId().toString())
                .userId(paymentRequestAvroModel.getUserId().toString())
                .orderId(paymentRequestAvroModel.getOrderId().toString())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }

    public PaymentResponseAvroModel orderEventPayloadToPaymentResponseAvroModel(String sagaId, OrderEventPayload orderEventPayload) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(sagaId))
                .setUserId(UUID.fromString(orderEventPayload.getUserId()))
                .setOrderId(UUID.fromString(orderEventPayload.getOrderId()))
                .setPaymentId(UUID.fromString(orderEventPayload.getPaymentId()))
                .setFailureMessages(orderEventPayload.getFailureMessages())
                .setPrice(orderEventPayload.getPrice())
                .setPaymentStatus(PaymentStatus.valueOf(orderEventPayload.getPaymentStatus()))
                .setCreatedAt(Instant.now())
                .build();
    }
}