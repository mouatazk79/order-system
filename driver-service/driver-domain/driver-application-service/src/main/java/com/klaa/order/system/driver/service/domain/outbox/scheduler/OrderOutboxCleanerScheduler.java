package com.klaa.order.system.driver.service.domain.outbox.scheduler;


import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.outbox.OutboxScheduler;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OrderOutboxCleanerScheduler implements OutboxScheduler {

    private final OrderOutboxHelper orderOutboxHelper;

    public OrderOutboxCleanerScheduler(OrderOutboxHelper orderOutboxHelper) {
        this.orderOutboxHelper = orderOutboxHelper;
    }

    @Transactional
    @Scheduled(fixedRate =12 ,timeUnit = TimeUnit.HOURS)
    @Override
    public void processOutboxMessages() {
        Optional<List<OrderOutboxMessage>> outboxMessagesResponse =
                orderOutboxHelper.getOrderOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
        if (outboxMessagesResponse.isPresent() && outboxMessagesResponse.get().size() > 0) {
            List<OrderOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            log.info("Received {} OrderOutboxMessage for clean-up!", outboxMessages.size());
            orderOutboxHelper.deleteOrderOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
            log.info("Deleted {} OrderOutboxMessage!", outboxMessages.size());
        }
    }


}