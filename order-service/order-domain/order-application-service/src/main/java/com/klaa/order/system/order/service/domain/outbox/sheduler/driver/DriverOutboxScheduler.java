package com.klaa.order.system.order.service.domain.outbox.sheduler.driver;

import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.order.service.domain.ports.output.publisher.driver.DriverRequestMessagePublisher;
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
public class DriverOutboxScheduler implements OutboxScheduler {
    private final DriverOutboxHelper driverOutboxHelper;
    private final DriverRequestMessagePublisher driverRequestMessagePublisher;
    @Override
    @Transactional
    @Scheduled(fixedRate =10,timeUnit = TimeUnit.SECONDS)
    public void processOutboxMessages() {
        Optional<List<DriverRequestOutboxMessage>> outboxMessagesResponse =
                driverOutboxHelper.getDriverRequestOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.STARTED);
        if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
            List<DriverRequestOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} DriverRequestOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(outboxMessage ->
                    driverRequestMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));
            log.info("{} DriverRequestOutboxMessage sent to message bus!", outboxMessages.size());

        }
    }

    private void updateOutboxStatus(DriverRequestOutboxMessage driverRequestOutboxMessage, OutboxStatus outboxStatus) {
        driverRequestOutboxMessage.setOutboxStatus(outboxStatus);
        driverOutboxHelper.save(driverRequestOutboxMessage);
        log.info("DriverRequestOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
