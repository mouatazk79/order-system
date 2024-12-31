package com.klaa.order.system.order.service.messaging.kafka.publisher;

import com.klaa.order.system.domain.order.service.domain.config.OrderServiceConfigData;
import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.order.service.domain.ports.output.publisher.reject.OrderRejectMessagePublisher;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import com.klaa.order.system.order.service.messaging.kafka.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RejectOrderMessagePublisher implements OrderRejectMessagePublisher {
    private final KafkaProducer<String, DriverRequestAvroModel> kafkaProducer;
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;

    public RejectOrderMessagePublisher(KafkaProducer<String, DriverRequestAvroModel> kafkaProducer, OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData) {
        this.kafkaProducer = kafkaProducer;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
    }

    @Override
    public void publish(OrderRejectedEvent domainEvent) {
        String orderId=domainEvent.getOrder().getId().toString();
        log.info("Received OrderRejectedEvent for order id: {}", orderId);
        try {
            DriverRequestAvroModel driverRequestAvroModel=
                    orderMessagingDataMapper.orderToDriverRequestAvroModel(domainEvent.getOrder());
            kafkaProducer.send(
                    orderServiceConfigData.getDriverApprovalRequestTopicName(),
                    orderId,
                    driverRequestAvroModel
            );
            log.info("DriverRequestAvroModel sent to Kafka for order id: {}", driverRequestAvroModel.getOrderId());
        }catch (Exception e){
            log.error("Error while sending PaymentRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }

    }
}
