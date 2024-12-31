package com.klaa.order.system.order.service.messaging.kafka.mapper;

import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
import org.springframework.stereotype.Component;

@Component
public class OrderMessagingDataMapper {
    public DriverRequestAvroModel orderToDriverRequestAvroModel(Order order) {
        return null;
    }

    public PaymentRequestAvroModel orderToPaymentRequestAvroModel(Order order) {
        return null;
    }
}
