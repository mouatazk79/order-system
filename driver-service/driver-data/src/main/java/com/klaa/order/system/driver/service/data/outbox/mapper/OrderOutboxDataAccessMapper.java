package com.klaa.order.system.driver.service.data.outbox.mapper;

import com.klaa.order.system.driver.service.data.outbox.entity.OrderOutboxEntity;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Slf4j
@Component
public class OrderOutboxDataAccessMapper {

    public OrderOutboxEntity orderOutboxMessageToOrderOutboxEntity(OrderOutboxMessage orderOutboxMessage) {
        return OrderOutboxEntity.builder()
                .id(orderOutboxMessage.getId())
                .sagaId(orderOutboxMessage.getSagaId())
                .orderId(orderOutboxMessage.getOrderId())
                .driverId(orderOutboxMessage.getDriverId())
                .createdAt(orderOutboxMessage.getCreatedAt())
                .processedAt(LocalDateTime.now())
                .type(orderOutboxMessage.getType())
                .outboxStatus(orderOutboxMessage.getOutboxStatus())
                .driverOrderStatus(orderOutboxMessage.getDriverOrderStatus())
                .version(orderOutboxMessage.getVersion())
                .build();
    }

    public OrderOutboxMessage orderOutboxEntityToOrderOutboxMessage(OrderOutboxEntity orderOutboxEntity) {
        log.info("OrderOutboxEntity : {}",orderOutboxEntity);
        return OrderOutboxMessage.builder()
                .id(orderOutboxEntity.getId())
                .sagaId(orderOutboxEntity.getSagaId())
                .driverId(orderOutboxEntity.getDriverId())
                .orderId(orderOutboxEntity.getOrderId())
                .createdAt(orderOutboxEntity.getCreatedAt())
                .type(orderOutboxEntity.getType())
                .outboxStatus(orderOutboxEntity.getOutboxStatus())
                .driverOrderStatus(orderOutboxEntity.getDriverOrderStatus())
                .version(orderOutboxEntity.getVersion())
                .build();
    }

}
