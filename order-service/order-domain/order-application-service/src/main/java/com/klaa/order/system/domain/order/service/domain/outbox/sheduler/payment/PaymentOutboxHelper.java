package com.klaa.order.system.domain.order.service.domain.outbox.sheduler.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestPayload;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.klaa.order.system.domain.valueobjects.OrderStatus;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentOutboxHelper {
    private final PaymentOutboxRepository paymentOutboxRepository;
    private final ObjectMapper objectMapper;


    @Transactional(readOnly = true)
    public Optional<List<PaymentRequestOutboxMessage>> getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus("OrderProcessingSaga",
                outboxStatus,
                sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<PaymentRequestOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID sagaId,
                                                                                            SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus("OrderProcessingSaga", sagaId, sagaStatus);
    }

    @Transactional
    public void save(PaymentRequestOutboxMessage paymentRequestOutboxMessage) {
        Optional<PaymentRequestOutboxMessage> newPaymentRequestOutboxMessage  = paymentOutboxRepository.save(paymentRequestOutboxMessage);
        if (newPaymentRequestOutboxMessage.isEmpty()) {
            log.error("Could not save PaymentRequestOutboxMessage with outbox id: {}", paymentRequestOutboxMessage.getId());
            throw new OrderDomainException("Could not save PaymentRequestOutboxMessage with outbox id: " +
                    paymentRequestOutboxMessage.getId());
        }
        log.info("PaymentRequestOutboxMessage saved with outbox id: {}", paymentRequestOutboxMessage.getId());
    }

    @Transactional
    public void savePaymentOutboxMessage(PaymentRequestPayload paymentRequestPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(PaymentRequestOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentRequestPayload.getCreatedAt())
                .type("OrderProcessingSaga")
                .payload(createPayload(paymentRequestPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatus) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus("OrderProcessingSaga", outboxStatus, sagaStatus);
    }

    private String createPayload(PaymentRequestPayload paymentRequestPayload) {
        try {
            return objectMapper.writeValueAsString(paymentRequestPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderPaymentEventPayload object for order id: {}",
                    paymentRequestPayload.getOrderId(), e);
            throw new OrderDomainException("Could not create OrderPaymentEventPayload object for order id: " +
                    paymentRequestPayload.getOrderId(), e);
        }
    }

}
