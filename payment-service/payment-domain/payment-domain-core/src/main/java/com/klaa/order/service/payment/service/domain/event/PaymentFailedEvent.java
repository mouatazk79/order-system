package com.klaa.order.service.payment.service.domain.event;

import com.klaa.order.service.payment.service.domain.entity.Payment;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent {


    public PaymentFailedEvent(Payment payment,
                              ZonedDateTime createdAt,
                              List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }

}
