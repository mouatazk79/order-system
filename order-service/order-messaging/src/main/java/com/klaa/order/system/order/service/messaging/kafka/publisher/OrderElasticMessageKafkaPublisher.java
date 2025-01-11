package com.klaa.order.system.order.service.messaging.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.order.service.domain.config.OrderServiceConfigData;
import com.klaa.order.system.domain.valueobjects.ElasticMessageStatus;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.order.service.domain.ports.output.publisher.elastic.OrderElasticMessagePublisher;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import com.klaa.order.system.order.service.messaging.kafka.mapper.OrderMessagingDataMapper;
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
    private final ObjectMapper objectMapper;
    @Override
    public void publish(OrderElasticMessage orderElasticMessage, BiConsumer<OrderElasticMessage, ElasticMessageStatus> elasticCallback) {
        Order orderPayload=getOrderPayload(orderElasticMessage.getPayload(),Order.class);
        OrderElasticMessageAvroModel orderElasticMessageAvroModel =
                orderMessagingDataMapper
                        .orderPayloadToOrderElasticMessageAvroModel(orderPayload);
        try {
            kafkaProducer.send(orderServiceConfigData.getOrderElasticTopicName(), orderElasticMessageAvroModel.getId().toString(), orderElasticMessageAvroModel);

            log.info("orderPayload sent to kafka for order id: {} and id: {}",
                    orderElasticMessageAvroModel.getId(), orderPayload.getId());
        } catch (Exception e) {
            log.error("Error while sending orderPayload to kafka for order id: {} and id: {}," +
                    " error: {}", orderElasticMessageAvroModel.getId(),  orderPayload.getId(), e.getMessage());
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
