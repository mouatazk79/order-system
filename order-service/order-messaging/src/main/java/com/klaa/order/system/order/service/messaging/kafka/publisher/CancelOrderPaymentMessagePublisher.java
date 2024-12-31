package com.klaa.order.system.order.service.messaging.kafka.publisher;

import com.klaa.order.system.domain.order.service.domain.config.OrderServiceConfigData;
import com.klaa.order.system.domain.order.service.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.order.service.domain.ports.output.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import com.klaa.order.system.order.service.messaging.kafka.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderPaymentMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;

    public CancelOrderPaymentMessagePublisher(KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData) {
        this.kafkaProducer = kafkaProducer;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
    }
    @Override
    public void publish(OrderCancelledEvent domainEvent) {
        String orderId=domainEvent.getOrder().getId().toString();
        log.info("Received OrderCancelledEvent for order id: {}", orderId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel=
                    orderMessagingDataMapper.orderToPaymentRequestAvroModel(domainEvent.getOrder());
            kafkaProducer.send(
                    orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel
            );
            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());
        }catch (Exception e){
            log.error("Error while sending DriverRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }


    }
}
