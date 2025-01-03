package com.klaa.order.system.domain.order.service.domain.ports.output.publisher.driver;

import com.klaa.order.system.domain.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface DriverRequestMessagePublisher {
    void publish(DriverRequestOutboxMessage driverRequestOutboxMessage,
                 BiConsumer<DriverRequestOutboxMessage, OutboxStatus> outboxCallback);
}
