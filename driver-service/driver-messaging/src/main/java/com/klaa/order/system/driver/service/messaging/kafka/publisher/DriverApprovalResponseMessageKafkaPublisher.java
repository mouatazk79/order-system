package com.klaa.order.system.driver.service.messaging.kafka.publisher;

import com.klaa.order.system.driver.service.domain.config.DriverServiceConfigData;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverApprovalResponseMessagePublisher;
import com.klaa.order.system.driver.service.messaging.kafka.mapper.DriverMessagingDataMapper;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
@Slf4j
@Component
@AllArgsConstructor
public class DriverApprovalResponseMessageKafkaPublisher implements DriverApprovalResponseMessagePublisher {
    private final DriverMessagingDataMapper driverMessagingDataMapper;
    private final KafkaProducer<String, DriverResponseAvroModel> kafkaProducer;
    private final DriverServiceConfigData driverServiceConfigData;
    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage, BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        String sagaId = orderOutboxMessage.getSagaId().toString();
        log.info("Received OrderOutboxMessage for order id: {} and saga id: {}",
                orderOutboxMessage.getOrderId(),
                sagaId);
        try {
            DriverResponseAvroModel driverResponseAvroModel =
                    driverMessagingDataMapper
                            .orderEventPayloadToDriverResponseAvroModel(sagaId, orderOutboxMessage);

            kafkaProducer.send(driverServiceConfigData.getDriverApprovalResponseTopicName(),
                    sagaId,
                    driverResponseAvroModel);

            log.info("DriverResponseAvroModel sent to kafka for order id: {} and saga id: {}",
                    driverResponseAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending DriverResponseAvroModel message" +
                            " to kafka with order id: {} and saga id: {}, error: {}",
                    orderOutboxMessage.getOrderId(), sagaId, e.getMessage());
        }
    }

}
