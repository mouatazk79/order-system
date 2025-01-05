package com.klaa.order.system.order.service.messaging.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.domain.order.service.domain.config.OrderServiceConfigData;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestPayload;
import com.klaa.order.system.domain.order.service.domain.ports.output.publisher.payment.PaymentRequestMessagePublisher;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
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
public class PaymentRequestMessageKafkaPublisher implements PaymentRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderServiceConfigData orderServiceConfigData;
    private final ObjectMapper objectMapper;
    @Override
    public void publish(PaymentRequestOutboxMessage paymentRequestOutboxMessage, BiConsumer<PaymentRequestOutboxMessage, OutboxStatus> outboxCallback) {
        PaymentRequestPayload paymentRequestPayload =
                getOrderEventPayload(paymentRequestOutboxMessage.getPayload(),
                        PaymentRequestPayload.class);

        String sagaId = paymentRequestOutboxMessage.getSagaId().toString();

        log.info("Received OrderPaymentOutboxMessage for order id: {} and saga id: {}",
                paymentRequestPayload.getOrderId(),
                sagaId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                    .orderPaymentEventToPaymentRequestAvroModel(sagaId, paymentRequestPayload);

            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    sagaId,
                    paymentRequestAvroModel);

            log.info("OrderPaymentEventPayload sent to Kafka for order id: {} and saga id: {}",
                    paymentRequestPayload.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending OrderPaymentEventPayload" +
                            " to kafka with order id: {} and saga id: {}, error: {}",
                    paymentRequestPayload.getOrderId(), sagaId, e.getMessage());
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
