package com.klaa.order.system.driver.service.messaging.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.driver.service.domain.config.DriverServiceConfigData;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderEventPayload;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverApprovalResponseMessagePublisher;
import com.klaa.order.system.driver.service.messaging.kafka.mapper.DriverMessagingDataMapper;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
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
    private final KafkaProducer<String, DriverRequestAvroModel> kafkaProducer;
    private final DriverServiceConfigData driverServiceConfigData;
    private final ObjectMapper objectMapper;
    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage, BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        OrderEventPayload orderEventPayload =
                getOrderEventPayload(orderOutboxMessage.getPayload(),
                        OrderEventPayload.class);

        String sagaId = orderOutboxMessage.getSagaId().toString();

        log.info("Received OrderOutboxMessage for order id: {} and saga id: {}",
                orderEventPayload.getOrderId(),
                sagaId);
        try {
            DriverRequestAvroModel driverRequestAvroModel =
                    driverMessagingDataMapper
                            .orderEventPayloadToDriverRequestAvroModel(sagaId, orderEventPayload);

            kafkaProducer.send(driverServiceConfigData.getDriverApprovalResponseTopicName(),
                    sagaId,
                    driverRequestAvroModel);

            log.info("DriverRequestAvroModel sent to kafka for order id: {} and saga id: {}",
                    driverRequestAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending DriverRequestAvroModel message" +
                            " to kafka with order id: {} and saga id: {}, error: {}",
                    orderEventPayload.getOrderId(), sagaId, e.getMessage());
        }
    }
    public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new OrderDomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }
}
