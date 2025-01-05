package com.klaa.order.system.order.service.messaging.kafka.mapper;

import com.klaa.order.system.domain.order.service.domain.dto.message.DriverResponse;
import com.klaa.order.system.domain.order.service.domain.dto.message.PaymentResponse;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.outbox.model.driver.DriverRequestPayload;
import com.klaa.order.system.domain.order.service.domain.outbox.model.payment.PaymentRequestPayload;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentResponseAvroModel;
import org.springframework.stereotype.Component;

@Component
public class OrderMessagingDataMapper {
    public DriverRequestAvroModel orderToDriverRequestAvroModel(Order order) {
        return null;
    }

    public PaymentRequestAvroModel orderToPaymentRequestAvroModel(Order order) {
        return null;
    }

    public DriverResponse driverResponseAvroModelToDriverReponse(DriverResponseAvroModel message) {
        return null;
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel message) {
        return null;
    }

    public DriverRequestAvroModel orderApprovalEventToDriverRequestAvroModel(String sagaId, DriverRequestPayload driverRequestPayload) {
        return null;
    }

    public PaymentRequestAvroModel orderPaymentEventToPaymentRequestAvroModel(String sagaId, PaymentRequestPayload paymentRequestPayload) {
        return null;
    }
}
