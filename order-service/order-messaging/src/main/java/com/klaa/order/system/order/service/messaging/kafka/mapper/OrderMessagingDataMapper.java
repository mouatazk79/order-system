package com.klaa.order.system.order.service.messaging.kafka.mapper;

import com.klaa.order.system.kafka.model.elastic.OrderStatus;
import com.klaa.order.system.order.service.domain.dto.create.PositionAddress;
import com.klaa.order.system.order.service.domain.dto.message.DriverResponse;
import com.klaa.order.system.order.service.domain.dto.message.PaymentResponse;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticPayload;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
public class
OrderMessagingDataMapper {

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

    public DriverRequestAvroModel driverRequestPayloadToDriverRequestAvroModel(String sagaId, DriverRequestPayload driverRequestPayload) {
        return DriverRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setDriverId(UUID.fromString(driverRequestPayload.getDriverId()))
                .setOrderStatus(com.klaa.order.system.kafka.model.driver.OrderStatus.valueOf(driverRequestPayload.getOrderStatus()))
                .setSagaId(UUID.fromString(sagaId))
                .setOrderId(UUID.fromString(driverRequestPayload.getOrderId()))
                .setPosition(positionAddressToPosition(driverRequestPayload.getPosition()))
                .setDestination(positionAddressToPosition(driverRequestPayload.getDestination()))
                .setPrice(driverRequestPayload.getPrice())
                .setCreatedAt(Instant.now())
                .build();
    }

    public PaymentRequestAvroModel paymentRequestPayloadToPaymentRequestAvroModel(String sagaId, PaymentRequestPayload paymentRequestPayload) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.fromString(sagaId))
                .setOrderId(UUID.fromString(paymentRequestPayload.getOrderId()))
                .setPrice(paymentRequestPayload.getPrice())
                .setUserId(UUID.fromString(paymentRequestPayload.getUserId()))
                .setCreatedAt(Instant.now())
                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestPayload.getPaymentOrderStatus()))
                .build();
    }

    public OrderElasticMessageAvroModel orderPayloadToOrderElasticMessageAvroModel(OrderElasticPayload orderElasticPayload) {
        return OrderElasticMessageAvroModel.newBuilder()
                .setId(UUID.fromString(orderElasticPayload.getOrderId().getValue()))
                .setTrackingId(UUID.fromString(orderElasticPayload.getTrackingId().getValue()))
                .setDriverId(UUID.fromString(orderElasticPayload.getDriverId().getValue()))
                .setOrderStatus(OrderStatus.valueOf(orderElasticPayload.getOrderStatus()))
                .setPosition(com.klaa.order.system.kafka.model.elastic.Position.newBuilder()
                        .setCity(orderElasticPayload.getPosition().getCity())
                        .setStreetAddress(orderElasticPayload.getPosition().getStreetAddress())
                        .setZipCode(orderElasticPayload.getPosition().getZipCode())
                        .build())
                .setDestination(com.klaa.order.system.kafka.model.elastic.Position.newBuilder()
                        .setCity(orderElasticPayload.getDestination().getCity())
                        .setStreetAddress(orderElasticPayload.getDestination().getStreetAddress())
                        .setZipCode(orderElasticPayload.getDestination().getZipCode())
                        .build())
                .setPrice(BigDecimal.valueOf(orderElasticPayload.getPrice().getAmount().doubleValue()))
                .build();
    }


    private Position positionAddressToPosition(PositionAddress positionAddress){
        return Position.newBuilder()
                .setCity(positionAddress.getCity())
                .setStreetAddress(positionAddress.getStreet())
                .setZipCode(positionAddress.getZipCode())
                .build();
    }

}
