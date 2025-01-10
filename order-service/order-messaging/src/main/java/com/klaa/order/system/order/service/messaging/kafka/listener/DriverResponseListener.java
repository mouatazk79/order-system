package com.klaa.order.system.order.service.messaging.kafka.listener;

import com.klaa.order.system.domain.order.service.domain.ports.input.listener.driver.DriverOrderResponseMessageListener;
import com.klaa.order.system.kafka.consumer.consumer.KafkaConsumer;
import com.klaa.order.system.kafka.model.driver.DriverOrderStatus;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import com.klaa.order.system.order.service.messaging.kafka.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class DriverResponseListener implements KafkaConsumer<DriverResponseAvroModel> {
    private final DriverOrderResponseMessageListener orderResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public DriverResponseListener(DriverOrderResponseMessageListener orderResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.orderResponseMessageListener = orderResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.driver-approval-consumer-group-id}",
            topics = "${order-service.driver-approval-response-topic-name}")
    public void receive(@Payload List<DriverResponseAvroModel> messages, @Header(KafkaHeaders.KEY) List<String> keys,@Header(KafkaHeaders.PARTITION) List<Integer> partitions,@Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        messages.forEach(message->{
            DriverOrderStatus messageDriverOrderStatus=message.getDriverOrderStatus();
            if (DriverOrderStatus.APPROVED==messageDriverOrderStatus){
                log.info("processing approved request with if: {}",message.getId());
                orderResponseMessageListener.orderApproved(
                        orderMessagingDataMapper.driverResponseAvroModelToDriverReponse(message)
                );
            } else if (DriverOrderStatus.REJECTED==messageDriverOrderStatus) {
                log.info("processing rejected request with if: {}",message.getId());
                orderResponseMessageListener.orderRejected(
                        orderMessagingDataMapper.driverResponseAvroModelToDriverReponse(message)
                );


            }


        });

    }
}
