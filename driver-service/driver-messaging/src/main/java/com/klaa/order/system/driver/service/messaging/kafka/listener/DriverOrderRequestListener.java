package com.klaa.order.system.driver.service.messaging.kafka.listener;

import com.klaa.order.system.driver.service.domain.ports.input.listener.DriverRequestListener;
import com.klaa.order.system.driver.service.messaging.kafka.mapper.DriverMessagingDataMapper;
import com.klaa.order.system.kafka.consumer.consumer.KafkaConsumer;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.model.driver.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DriverOrderRequestListener implements KafkaConsumer<DriverRequestAvroModel> {
    private final DriverRequestListener driverRequestListener;
    private final DriverMessagingDataMapper driverMessagingDataMapper;

    public DriverOrderRequestListener(DriverRequestListener driverRequestListener, DriverMessagingDataMapper driverMessagingDataMapper) {
        this.driverRequestListener = driverRequestListener;
        this.driverMessagingDataMapper = driverMessagingDataMapper;
    }

    @Override
    public void receive(@Payload List<DriverRequestAvroModel> messages, @Header(KafkaHeaders.KEY) List<String> keys,@Header(KafkaHeaders.PARTITION) List<Integer> partitions,@Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        messages.forEach(
                message->{
                    log.info("receiving order with id: {}",message.getOrderId());
                    OrderStatus messageOrderStatus=message.getOrderStatus();
                    if (OrderStatus.PENDING==messageOrderStatus){
                        log.info("processing pending order with id: {}",message.getOrderId());
                        driverRequestListener.orderRequest(driverMessagingDataMapper.driverRequestAvroModelToDriverRequest(message));
                    } else if (OrderStatus.REJECTING==messageOrderStatus||OrderStatus.CANCELLING==messageOrderStatus) {
                        log.info("processing cancelled order with id: {}",message.getOrderId());
                        driverRequestListener.orderCancelled(
                                driverMessagingDataMapper.driverRequestAvroModelToDriverRequest(message)
                        );
                    }
                }
        );

    }
}
