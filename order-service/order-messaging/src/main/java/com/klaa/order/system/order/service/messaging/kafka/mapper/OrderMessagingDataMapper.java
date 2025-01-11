package com.klaa.order.system.order.service.messaging.kafka.mapper;

import com.klaa.order.system.order.service.domain.dto.create.PositionAddress;
import com.klaa.order.system.order.service.domain.dto.message.DriverResponse;
import com.klaa.order.system.order.service.domain.dto.message.PaymentResponse;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestPayload;
import com.klaa.order.system.order.service.domain.outbox.model.payment.PaymentRequestPayload;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.kafka.model.driver.DriverRequestAvroModel;
import com.klaa.order.system.kafka.model.driver.DriverResponseAvroModel;
import com.klaa.order.system.kafka.model.driver.Position;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentOrderStatus;
import com.klaa.order.system.kafka.model.payment.PaymentRequestAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentResponseAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class
OrderMessagingDataMapper {
//    public DriverRequestAvroModel orderToDriverRequestAvroModel(Order order) {
//        return DriverRequestAvroModel.newBuilder()
//                .setId(UUID.randomUUID().toString())
//                .setSagaId(order.get)
//                .setCustomerId(orderPaymentEventPayload.getCustomerId())
//                .setOrderId(orderPaymentEventPayload.getOrderId())
//                .setPrice(orderPaymentEventPayload.getPrice())
//                .setCreatedAt(orderPaymentEventPayload.getCreatedAt().toInstant())
//                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(orderPaymentEventPayload.getPaymentOrderStatus()))
//                .build();
//
//
//    }
//
//    public PaymentRequestAvroModel orderToPaymentRequestAvroModel(Order order) {
//        return PaymentRequestAvroModel.newBuilder()
//                .setId(UUID.randomUUID())
////                .setSagaId()
//                .setOrderId(order.getId().getValue())
//                .setPaymentOrderStatus(order.get)
//                ;
//    }

    public DriverResponse driverResponseAvroModelToDriverResponse(DriverResponseAvroModel driverResponseAvroModel) {
        return DriverResponse.builder()
                .id(driverResponseAvroModel.getId().toString())
                .sagaId(driverResponseAvroModel.getSagaId().toString())
                .orderId(driverResponseAvroModel.getOrderId())
                .driverId(driverResponseAvroModel.getDriverId().toString())
                .createdAt(driverResponseAvroModel.getCreatedAt())
                .driverOrderStatus(DriverOrderStatus.valueOf(driverResponseAvroModel.getDriverOrderStatus().toString()))
                .failureMessages(driverResponseAvroModel.getFailureMessages())
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId().toString())
                .sagaId(paymentResponseAvroModel.getSagaId().toString())
                .orderId(paymentResponseAvroModel.getOrderId())
                .paymentId(paymentResponseAvroModel.getPaymentId().toString())
                .userId(paymentResponseAvroModel.getUserId().toString())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(com.klaa.order.system.domain.valueobjects.PaymentOrderStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().toString()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public DriverRequestAvroModel orderApprovalEventToDriverRequestAvroModel(String sagaId, DriverRequestPayload driverRequestPayload) {
        return DriverRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(sagaId))
                .setOrderId(UUID.fromString(driverRequestPayload.getOrderId()))
                .setPosition(positionAddressToPosition(driverRequestPayload.getPosition()))
                .setDestination(positionAddressToPosition(driverRequestPayload.getDestination()))
                .setPrice(driverRequestPayload.getPrice())
//                .setCreatedAt(Instant.)
                .build();


    }

    public PaymentRequestAvroModel orderPaymentEventToPaymentRequestAvroModel(String sagaId, PaymentRequestPayload paymentRequestPayload) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(sagaId))
                .setOrderId(UUID.fromString(paymentRequestPayload.getOrderId()))
                .setPrice(paymentRequestPayload.getPrice())
                .setUserId(UUID.fromString(paymentRequestPayload.getUserId()))
                .setCreatedAt(paymentRequestPayload.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestPayload.getPaymentOrderStatus()))
                .build();
    }

    public OrderElasticMessageAvroModel orderPayloadToOrderElasticMessageAvroModel(Order orderPayload) {
        return null;
    }

    private Position positionAddressToPosition(PositionAddress positionAddress){
        return Position.newBuilder()
                .setCity(positionAddress.getCity())
                .setStreetAddress(positionAddress.getStreet())
                .setZipCode(positionAddress.getZipCode())
                .build();
    }

}
