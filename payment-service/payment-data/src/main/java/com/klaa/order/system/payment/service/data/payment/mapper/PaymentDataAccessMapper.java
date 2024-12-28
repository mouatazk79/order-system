package com.klaa.order.system.payment.service.data.payment.mapper;


import com.klaa.order.service.payment.service.domain.entity.Payment;
import com.klaa.order.service.payment.service.domain.valueobject.PaymentId;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.payment.service.data.payment.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .userId(payment.getUserId().getValue())
                .orderId(payment.getOrderId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .userId(new UserId(paymentEntity.getUserId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .price(new Money(paymentEntity.getPrice()))
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }

}
