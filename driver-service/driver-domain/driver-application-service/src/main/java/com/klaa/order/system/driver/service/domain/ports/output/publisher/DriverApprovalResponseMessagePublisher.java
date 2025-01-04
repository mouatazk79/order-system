package com.klaa.order.system.driver.service.domain.ports.output.publisher;



import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface DriverApprovalResponseMessagePublisher {

    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
