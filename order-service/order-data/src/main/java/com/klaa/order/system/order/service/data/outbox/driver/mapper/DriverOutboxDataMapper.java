package com.klaa.order.system.order.service.data.outbox.driver.mapper;

import com.klaa.order.system.domain.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.order.service.data.outbox.driver.entity.DriverOutboxMessageEntity;

import java.util.Optional;

public class DriverOutboxDataMapper {
    public Optional<DriverRequestOutboxMessage> approvalOutboxEntityToOrderApprovalOutboxMessage(DriverOutboxMessageEntity driverOutboxMessageEntity) {
        return null;
    }

    public DriverOutboxMessageEntity driverOutboxMessageToOutDriverOutboxMessageEntity(DriverRequestOutboxMessage driverRequestOutboxMessage) {
        return null;
    }

    public DriverRequestOutboxMessage driverOutboxMessageEntityToDriverOutboxMessage(DriverOutboxMessageEntity driverOutboxMessageEntity) {
        return null;
    }
}
