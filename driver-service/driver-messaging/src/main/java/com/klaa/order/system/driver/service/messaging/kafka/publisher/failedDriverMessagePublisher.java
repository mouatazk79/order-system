package com.klaa.order.system.driver.service.messaging.kafka.publisher;

import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;
import com.klaa.order.system.driver.service.domain.config.DriverServiceConfigData;
import com.klaa.order.system.driver.service.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.driver.service.messaging.kafka.mapper.DriverMessagingDataMapper;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class failedDriverMessagePublisher implements DomainEventPublisher<OrderDriverFailedEvent> {
    private final KafkaProducer<String, DriverResponseAvroModel> kafkaProducer;
    private final DriverMessagingDataMapper driverMessagingDataMapper;
    private final DriverServiceConfigData driverServiceConfigData;

    public failedDriverMessagePublisher(KafkaProducer<String, DriverResponseAvroModel> kafkaProducer, DriverMessagingDataMapper driverMessagingDataMapper, DriverServiceConfigData driverServiceConfigData) {
        this.kafkaProducer = kafkaProducer;
        this.driverMessagingDataMapper = driverMessagingDataMapper;
        this.driverServiceConfigData = driverServiceConfigData;
    }


    @Override
    public void publish(OrderDriverFailedEvent domainEvent) {
        String orderApprovalId=domainEvent.getOrderApproval().getId().toString();
        log.info("Received OrderDriverFailedEvent for order id: {}", orderApprovalId);
        try {
            DriverResponseAvroModel driverResponseAvroModel=
                    driverMessagingDataMapper.orderApprovalToDriverResponseAvroModel(domainEvent.getOrderApproval());
            kafkaProducer.send(
                    driverServiceConfigData.getDriverApprovalResponseTopicName(),
                    orderApprovalId,
                    driverResponseAvroModel
            );
            log.info("DriverResponseAvroModel sent to Kafka for order id: {}", driverResponseAvroModel.getOrderId());
        }catch (Exception e){
            log.error("Error while sending PaymentRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderApprovalId, e.getMessage());
        }


    }

    }

