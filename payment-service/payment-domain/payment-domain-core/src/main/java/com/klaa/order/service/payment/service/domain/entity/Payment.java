package com.klaa.order.service.payment.service.domain.entity;


import com.klaa.order.service.payment.service.domain.valueobject.PaymentId;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.domain.valueobjects.PaymentStatus;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.driver.service.domain.entity.AggregateRoot;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Payment extends AggregateRoot<PaymentId> {

    private final OrderId orderId;
    private final UserId userId;
    private final Money price;

    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    public void initializePayment() {
        setId(new PaymentId(UUID.randomUUID()));
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public void validatePayment(List<String> failureMessages) {
        if (price == null || !price.isGreaterThanZero()) {
            failureMessages.add("Total price must be greater than zero!");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    private Payment(Builder builder) {
        setId(builder.paymentId);
        orderId = builder.orderId;
        userId = builder.userId;
        price = builder.price;
        paymentStatus = builder.paymentStatus;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public UserId getUserId() {
        return userId;
    }

    public Money getPrice() {
        return price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public static final class Builder {
        private PaymentId paymentId;
        private OrderId orderId;
        private UserId userId;
        private Money price;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;

        private Builder() {
        }

        public Builder paymentId(PaymentId val) {
            paymentId = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
