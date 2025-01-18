package com.klaa.order.system.order.service.messaging.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.order.service.domain.config.OrderServiceConfigData;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestPayload;
import com.klaa.order.system.order.service.domain.ports.output.publisher.driver.DriverRequestMessagePublisher;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import com.klaa.order.system.order.service.messaging.kafka.mapper.OrderMessagingDataMapper;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
@AllArgsConstructor
public class DriverRequestMessageKafkaPublisher implements DriverRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, DriverRequestAvroModel> kafkaProducer;
    private final OrderServiceConfigData orderServiceConfigData;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(DriverRequestOutboxMessage driverRequestOutboxMessage, BiConsumer<DriverRequestOutboxMessage, OutboxStatus> outboxCallback) {
        DriverRequestPayload  driverRequestPayload =getOrderEventPayload(driverRequestOutboxMessage.getPayload(), DriverRequestPayload.class);

        String sagaId = driverRequestOutboxMessage.getSagaId().toString();

        log.info("Received OrderApprovalOutboxMessage for order id: {} and saga id: {}",
                driverRequestPayload.getOrderId(),
                sagaId);
        log.info("topic name {}",orderServiceConfigData.getDriverApprovalRequestTopicName());

        try {

            DriverRequestAvroModel driverRequestAvroModel =
                    orderMessagingDataMapper
                            .driverRequestPayloadToDriverRequestAvroModel(sagaId,
                                    driverRequestPayload);
            log.info("DriverRequestAvroModel with orderId {}",driverRequestAvroModel.getOrderId());

            kafkaProducer.send(orderServiceConfigData.getDriverApprovalRequestTopicName(),
                    sagaId,
                    driverRequestAvroModel);

            log.info("DriverRequestPayload sent to kafka for order id: {} and saga id: {}",
                    driverRequestAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending DriverRequestPayload to kafka for order id: {} and saga id: {}," +
                    " error: {}", driverRequestPayload.getOrderId(), sagaId, e.getMessage());
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
