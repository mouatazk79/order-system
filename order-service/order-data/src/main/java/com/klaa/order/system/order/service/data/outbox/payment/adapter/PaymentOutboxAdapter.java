package com.klaa.order.system.order.service.data.outbox.payment.adapter;

import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.klaa.order.system.order.service.data.outbox.payment.exception.PaymentOutboxDataException;
import com.klaa.order.system.order.service.data.outbox.payment.mapper.PaymentOutboxDataMapper;
import com.klaa.order.system.order.service.data.outbox.payment.repository.PaymentOutboxJpaRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentOutboxAdapter implements PaymentOutboxRepository {
    private final PaymentOutboxJpaRepository paymentOutboxJpaRepository;
    private final PaymentOutboxDataMapper paymentOutboxDataMapper;
    @Override
    public Optional<PaymentRequestOutboxMessage> save(PaymentRequestOutboxMessage paymentRequestOutboxMessage) {
        return Optional.of(paymentOutboxDataMapper
                .paymentOutboxMessageEntityToPaymentRequestOutboxMessage(paymentOutboxJpaRepository
                        .save(paymentOutboxDataMapper
                                .paymentRequestOutboxMessageToPaymentOutboxMessageEntity(paymentRequestOutboxMessage))));
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {

        paymentOutboxJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus,
                Arrays.asList(sagaStatus));

    }

    @Override
    public Optional<List<PaymentRequestOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return Optional.of(paymentOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(type,
                        outboxStatus,
                        Arrays.asList(sagaStatus))
                .orElseThrow(() -> new PaymentOutboxDataException("Payment outbox object " +
                        "could not be found for saga type " + type))
                .stream()
                .map(paymentOutboxDataMapper::paymentOutboxMessageEntityToPaymentRequestOutboxMessage)
                .collect(Collectors.toList()));    }

    @Override
    public Optional<PaymentRequestOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type, UUID sagaId, SagaStatus... sagaStatus) {
        return paymentOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId, Arrays.asList(sagaStatus))
                .map(paymentOutboxDataMapper::paymentOutboxMessageEntityToPaymentRequestOutboxMessage);    }
}
