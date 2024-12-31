package com.klaa.order.system.driver.service.messaging.kafka.mapper;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DriverMessagingDataMapper {
    public DriverResponseAvroModel orderApprovalToDriverResponseAvroModel(OrderApproval orderApproval) {
        return null;
    }
}
