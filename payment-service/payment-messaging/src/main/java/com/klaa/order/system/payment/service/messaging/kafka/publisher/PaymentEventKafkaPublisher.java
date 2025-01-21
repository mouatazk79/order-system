package com.klaa.order.system.payment.service.messaging.kafka.publisher;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.kafka.model.payment.PaymentResponseAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaHelper;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.payment.service.domain.config.PaymentServiceConfigData;
import com.klaa.order.system.payment.service.domain.outbox.model.OrderEventPayload;
import com.klaa.order.system.payment.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.payment.service.domain.ports.output.message.publisher.PaymentResponseMessagePublisher;
import com.klaa.order.system.payment.service.messaging.kafka.mapper.PaymentMessagingDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentEventKafkaPublisher implements PaymentResponseMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final ObjectMapper objectMapper;
    private final KafkaHelper kafkaHelper;




    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage,
                        BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        OrderEventPayload orderEventPayload =
                getOrderEventPayload(orderOutboxMessage.getPayload(), OrderEventPayload.class);

        String sagaId = orderOutboxMessage.getSagaId().toString();

        log.info("Received OrderOutboxMessage for order id: {} and saga id: {}",
                orderEventPayload.getOrderId(),
                sagaId);

        try {
            PaymentResponseAvroModel paymentResponseAvroModel = paymentMessagingDataMapper
                    .orderEventPayloadToPaymentResponseAvroModel(sagaId, orderEventPayload);

            kafkaProducer.send(paymentServiceConfigData.getPaymentResponseTopicName(),
                    sagaId,
                    paymentResponseAvroModel,
                    kafkaHelper.getKafkaCallback(
                            paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderOutboxMessage,
                            outboxCallback,
                            orderEventPayload.getOrderId(),
                            "PaymentResponseAvroModel"
                    )
                    );

            log.info("PaymentResponseAvroModel sent to kafka for order id: {} and saga id: {}",
                    paymentResponseAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message" +
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
