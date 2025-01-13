package com.klaa.order.system.order.service.domain.outbox.sheduler.driver;

import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
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
public class DriverOutboxSchedulerCleaner implements OutboxScheduler {
    private final DriverOutboxHelper driverOutboxHelper;
    @Override
    @Transactional
    @Scheduled(fixedRate =12 ,timeUnit = TimeUnit.HOURS)
    public void processOutboxMessages() {
        Optional<List<DriverRequestOutboxMessage>> outboxMessagesResponse =
                driverOutboxHelper.getDriverRequestOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED);
        if (outboxMessagesResponse.isPresent()) {
            List<DriverRequestOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} OrderApprovalOutboxMessage for clean-up. The payloads: {}",
                    outboxMessages.size(),
                    outboxMessages.stream().map(DriverRequestOutboxMessage::getPayload)
                            .collect(Collectors.joining("\n")));
            driverOutboxHelper.deleteDriverOutboxMessageByOutboxStatusAndSagaStatus(
                    OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED);
            log.info("{} OrderApprovalOutboxMessage deleted!", outboxMessages.size());
        }

    }
}
