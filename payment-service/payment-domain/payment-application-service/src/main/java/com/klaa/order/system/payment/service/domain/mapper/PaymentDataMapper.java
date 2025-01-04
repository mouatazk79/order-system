package com.klaa.order.system.payment.service.domain.mapper;



import com.klaa.order.service.payment.service.domain.entity.Payment;
import com.klaa.order.service.payment.service.domain.event.PaymentEvent;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.payment.service.domain.dto.PaymentRequest;
import com.klaa.order.system.payment.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .userId(new UserId(UUID.fromString(paymentRequest.getUserId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
    public OrderEventPayload paymentEventToOrderEventPayload(PaymentEvent paymentEvent) {
        return OrderEventPayload.builder()
                .paymentId(paymentEvent.getPayment().getId().getValue().toString())
                .customerId(paymentEvent.getPayment().getUserId().getValue().toString())
                .orderId(paymentEvent.getPayment().getOrderId().getValue().toString())
                .price(paymentEvent.getPayment().getPrice().getAmount())
                .createdAt(paymentEvent.getCreatedAt())
                .paymentStatus(paymentEvent.getPayment().getPaymentStatus().name())
                .failureMessages(paymentEvent.getFailureMessages())
                .build();
    }

}
