package com.klaa.order.system.payment.service.messaging.mapper;


import com.klaa.order.service.payment.service.domain.event.PaymentCancelledEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentCompletedEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentFailedEvent;
import com.klaa.order.system.domain.valueobjects.PaymentOrderStatus;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentResponseAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentStatus;
import com.klaa.order.system.payment.service.domain.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {

    public PaymentResponseAvroModel
    paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent paymentCompletedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(""))
                .setPaymentId(paymentCompletedEvent.getPayment().getId().getValue())
                .setUserId(paymentCompletedEvent.getPayment().getUserId().getValue())
                .setOrderId(paymentCompletedEvent.getPayment().getOrderId().getValue())
                .setPrice(paymentCompletedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCompletedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCompletedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCompletedEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel
    paymentCancelledEventToPaymentResponseAvroModel(PaymentCancelledEvent paymentCancelledEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(""))
                .setPaymentId(paymentCancelledEvent.getPayment().getId().getValue())
                .setUserId(paymentCancelledEvent.getPayment().getUserId().getValue())
                .setOrderId(paymentCancelledEvent.getPayment().getOrderId().getValue())
                .setPrice(paymentCancelledEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCancelledEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCancelledEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCancelledEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel
    paymentFailedEventToPaymentResponseAvroModel(PaymentFailedEvent paymentFailedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(""))
                .setPaymentId(paymentFailedEvent.getPayment().getId().getValue())
                .setUserId(paymentFailedEvent.getPayment().getUserId().getValue())
                .setOrderId(paymentFailedEvent.getPayment().getOrderId().getValue())
                .setPrice(paymentFailedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentFailedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentFailedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentFailedEvent.getFailureMessages())
                .build();
    }

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
}
