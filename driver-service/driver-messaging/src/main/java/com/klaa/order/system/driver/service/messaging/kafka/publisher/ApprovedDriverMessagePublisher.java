package com.klaa.order.system.driver.service.messaging.kafka.publisher;

import com.klaa.order.system.driver.service.domain.config.DriverServiceConfigData;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverApprovedRequestMessagePublisher;
import com.klaa.order.system.driver.service.messaging.kafka.mapper.DriverMessagingDataMapper;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import com.klaa.order.system.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApprovedDriverMessagePublisher implements DriverApprovedRequestMessagePublisher {
    private final KafkaProducer<String, DriverResponseAvroModel> kafkaProducer;
    private final DriverMessagingDataMapper driverMessagingDataMapper;
    private final DriverServiceConfigData driverServiceConfigData;

    public ApprovedDriverMessagePublisher(KafkaProducer<String, DriverResponseAvroModel> kafkaProducer, DriverMessagingDataMapper driverMessagingDataMapper, DriverServiceConfigData driverServiceConfigData) {
        this.kafkaProducer = kafkaProducer;
        this.driverMessagingDataMapper = driverMessagingDataMapper;
        this.driverServiceConfigData = driverServiceConfigData;
    }

    @Override
    public void publish(OrderDriverApprovedEvent domainEvent) {
        String orderApprovalId=domainEvent.getOrderApproval().getId().toString();
        log.info("Received OrderDriverApprovedEvent for order id: {}", orderApprovalId);
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
