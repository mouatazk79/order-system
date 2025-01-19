package com.klaa.order.system.driver.service.messaging.kafka.mapper;

import com.klaa.order.system.domain.valueobjects.Position;
import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.kafka.model.driver.DriverOrderStatus;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
public class DriverMessagingDataMapper {

    public DriverRequest driverRequestAvroModelToDriverRequest(DriverRequestAvroModel driverRequestAvroModel) {
        return  DriverRequest.builder()
                .id(driverRequestAvroModel.getId().toString())
                .sagaId(driverRequestAvroModel.getSagaId().toString())
                .driverId(driverRequestAvroModel.getDriverId())
                .orderId(driverRequestAvroModel.getOrderId())
                .driverOrderStatus(com.klaa.order.system.domain.valueobjects.DriverOrderStatus.PENDING)
                .position(new Position(UUID.randomUUID(),driverRequestAvroModel.getPosition().getStreetAddress(),driverRequestAvroModel.getPosition().getZipCode(),driverRequestAvroModel.getPosition().getCity()) )
                .destination(new Position(UUID.randomUUID(),driverRequestAvroModel.getDestination().getStreetAddress(),driverRequestAvroModel.getDestination().getZipCode(),driverRequestAvroModel.getDestination().getCity()))
                .price(driverRequestAvroModel.getPrice())
                .createdAt(driverRequestAvroModel.getCreatedAt())
                .build();

    }

    public DriverResponseAvroModel orderEventPayloadToDriverResponseAvroModel(String sagaId, OrderOutboxMessage orderOutboxMessage) {
        return DriverResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(sagaId))
                .setDriverId(orderOutboxMessage.getDriverId())
                .setOrderId(orderOutboxMessage.getOrderId())
                .setDriverOrderStatus(DriverOrderStatus.valueOf(orderOutboxMessage.getDriverOrderStatus().name()))
                .setCreatedAt(Instant.now())
                .setFailureMessages(Collections.emptyList())
                .build();
    }
}
