package com.klaa.order.system.order.service.domain.outbox.sheduler.payment;

import com.klaa.order.system.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.order.service.domain.ports.output.publisher.payment.PaymentRequestMessagePublisher;
import com.klaa.order.system.outbox.OutboxScheduler;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentOutboxScheduler implements OutboxScheduler {
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final PaymentRequestMessagePublisher paymentRequestMessagePublisher;
    @Override
    @Transactional
    @Scheduled(fixedRate =10,timeUnit = TimeUnit.SECONDS)
    public void processOutboxMessages() {
        Optional<List<PaymentRequestOutboxMessage>> paymentRequestOutboxMessage =
                paymentOutboxHelper.getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.PROCESSING,
                        SagaStatus.COMPENSATING);

        if (paymentRequestOutboxMessage.isPresent() && paymentRequestOutboxMessage.get().size() > 0) {
            List<PaymentRequestOutboxMessage> outboxMessages = paymentRequestOutboxMessage.get();
            log.info("Received {} PaymentRequestOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(outboxMessage ->
                    paymentRequestMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));
            log.info("{} PaymentRequestOutboxMessage sent to message bus!", outboxMessages.size());
        }

    }

    private void updateOutboxStatus(PaymentRequestOutboxMessage paymentRequestOutboxMessage, OutboxStatus outboxStatus) {
        paymentRequestOutboxMessage.setOutboxStatus(outboxStatus);
        paymentOutboxHelper.save(paymentRequestOutboxMessage);
        log.info("PaymentRequestOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
