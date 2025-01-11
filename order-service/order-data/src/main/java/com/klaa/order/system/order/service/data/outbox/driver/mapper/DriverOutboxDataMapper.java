package com.klaa.order.system.order.service.data.outbox.driver.mapper;

import com.klaa.order.system.domain.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.order.service.data.outbox.driver.entity.DriverOutboxMessageEntity;

import java.util.Optional;

public class DriverOutboxDataMapper {
    public Optional<DriverRequestOutboxMessage> approvalOutboxEntityToOrderApprovalOutboxMessage(DriverOutboxMessageEntity driverOutboxMessageEntity) {
        return Optional.of(DriverRequestOutboxMessage.builder()
                .id(driverOutboxMessageEntity.getId())
                .sagaId(driverOutboxMessageEntity.getSagaId())
                .createdAt(driverOutboxMessageEntity.getCreatedAt())
                .processedAt(driverOutboxMessageEntity.getProcessedAt())
                .type(driverOutboxMessageEntity.getType())
                .payload(driverOutboxMessageEntity.getPayload())
                .sagaStatus(driverOutboxMessageEntity.getSagaStatus())
                .orderStatus(driverOutboxMessageEntity.getOrderStatus())
                .outboxStatus(driverOutboxMessageEntity.getOutboxStatus())
                .version(driverOutboxMessageEntity.getVersion())
                .build());
    }

    public DriverOutboxMessageEntity driverOutboxMessageToOutDriverOutboxMessageEntity(DriverRequestOutboxMessage driverRequestOutboxMessage) {
        return DriverOutboxMessageEntity.builder()
                .id(driverRequestOutboxMessage.getId())
                .sagaId(driverRequestOutboxMessage.getSagaId())
                .createdAt(driverRequestOutboxMessage.getCreatedAt())
                .processedAt(driverRequestOutboxMessage.getProcessedAt())
                .type(driverRequestOutboxMessage.getType())
                .payload(driverRequestOutboxMessage.getPayload())
                .sagaStatus(driverRequestOutboxMessage.getSagaStatus())
                .outboxStatus(driverRequestOutboxMessage.getOutboxStatus())
                .version(driverRequestOutboxMessage.getVersion())
                .build();
    }

    public DriverRequestOutboxMessage driverOutboxMessageEntityToDriverOutboxMessage(DriverOutboxMessageEntity driverOutboxMessageEntity) {
        return DriverRequestOutboxMessage.builder()
                .id(driverOutboxMessageEntity.getId())
                .sagaId(driverOutboxMessageEntity.getSagaId())
                .createdAt(driverOutboxMessageEntity.getCreatedAt())
                .processedAt(driverOutboxMessageEntity.getProcessedAt())
                .type(driverOutboxMessageEntity.getType())
                .payload(driverOutboxMessageEntity.getPayload())
                .sagaStatus(driverOutboxMessageEntity.getSagaStatus())
                .orderStatus(driverOutboxMessageEntity.getOrderStatus())
                .outboxStatus(driverOutboxMessageEntity.getOutboxStatus())
                .version(driverOutboxMessageEntity.getVersion())
                .build();
    }
}
