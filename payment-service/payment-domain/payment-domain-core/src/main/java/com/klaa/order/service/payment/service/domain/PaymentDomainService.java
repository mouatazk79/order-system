package com.klaa.order.service.payment.service.domain;



import com.klaa.order.service.payment.service.domain.entity.CreditEntry;
import com.klaa.order.service.payment.service.domain.entity.CreditHistory;
import com.klaa.order.service.payment.service.domain.entity.Payment;
import com.klaa.order.service.payment.service.domain.event.PaymentCancelledEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentCompletedEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentFailedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages,
                                            DomainEventPublisher<PaymentCompletedEvent>
                                                    paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages, DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);
}
