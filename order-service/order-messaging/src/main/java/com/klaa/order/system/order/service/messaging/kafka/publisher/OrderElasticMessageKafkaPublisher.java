package com.klaa.order.system.order.service.messaging.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.kafka.producer.service.KafkaHelper;
import com.klaa.order.system.order.service.domain.config.OrderServiceConfigData;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticPayload;
import com.klaa.order.system.order.service.domain.ports.output.publisher.elastic.OrderElasticMessagePublisher;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
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
public class OrderElasticMessageKafkaPublisher implements OrderElasticMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, OrderElasticMessageAvroModel> kafkaProducer;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaHelper kafkaHelper;
    private final ObjectMapper objectMapper;
    @Override
    public void publish(OrderElasticMessage orderElasticMessage, BiConsumer<OrderElasticMessage, OutboxStatus> elasticCallback) {
        OrderElasticPayload orderElasticPayload=getOrderPayload(orderElasticMessage.getPayload(),OrderElasticPayload.class);
        OrderElasticMessageAvroModel orderElasticMessageAvroModel =
                orderMessagingDataMapper
                        .orderPayloadToOrderElasticMessageAvroModel(orderElasticPayload);
        try {
            kafkaProducer.send(
                    orderServiceConfigData.getOrderElasticTopicName(),
                    orderElasticMessageAvroModel.getId().toString(),
                    orderElasticMessageAvroModel,
                    kafkaHelper.getKafkaCallback(
                            orderServiceConfigData.getOrderElasticTopicName(),
                            orderElasticMessageAvroModel,
                            orderElasticMessage,
                            elasticCallback,
                            orderElasticPayload.getOrderId().getValue(),
                            "OrderElasticMessageAvroModel"
                    )
            );

            log.info("orderPayload sent to kafka for order id: {} and id: {}",
                    orderElasticMessageAvroModel.getId(), orderElasticPayload.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending orderPayload to kafka for order id: {} and id: {}," +
                    " error: {}", orderElasticMessageAvroModel.getId(),  orderElasticPayload.getOrderId(), e.getMessage());
        }


    }

    public <T> T getOrderPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new OrderDomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }
}
