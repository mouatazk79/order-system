package com.klaa.order.system.order.service.domain.mapper;

import com.klaa.order.system.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.order.service.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.order.service.domain.dto.reject.RejectOrderResponse;
import com.klaa.order.system.order.service.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.order.service.domain.dto.create.PositionAddress;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestPayload;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.Position;
import com.klaa.order.system.domain.valueobjects.UserId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderDataMapper {

    public Order  orderCreateCommandToOrder(OrderCreateCommand orderCreateCommand){
        return Order.builder()
                .userId(new UserId(orderCreateCommand.getUserId()))
                .position(positionAddressToPosition(orderCreateCommand.getPosition()))
                .destination(positionAddressToPosition(orderCreateCommand.getDestinationAddress()))
                .price(new Money(orderCreateCommand.getPrice()))
                .build();
    }
    public OrderCreateResponse orderCreatedEventToOrderCreateResponse(OrderCreatedEvent orderCreatedEvent) {
        return OrderCreateResponse.builder()
                .orderStatus(orderCreatedEvent.getOrder().getOrderStatus())
                .orderTrackingId(orderCreatedEvent.getOrder().getTrackingId().getValue())
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    public RejectOrderResponse orderRejectedEventToRejectOrderResponse(OrderRejectedEvent rejectedEvent) {
        return RejectOrderResponse.builder()
                .orderTrackingId(rejectedEvent.getOrder().getTrackingId().getValue())
                .orderStatus(rejectedEvent.getOrder().getOrderStatus())
                .build();
    }

    public DriverRequestPayload orderCreatedEventToDriverRequestPayload(OrderCreatedEvent orderCreatedEvent) {
        return  DriverRequestPayload.builder()
                .orderId(orderCreatedEvent.getOrder().getId().getValue().toString())
                .driverId(orderCreatedEvent.getOrder().getDriverId().toString())
                .position(orderAddressToStreetAddress(orderCreatedEvent.getOrder().getPosition()))
                .destination(orderAddressToStreetAddress(orderCreatedEvent.getOrder().getDestination()))
                .price(orderCreatedEvent.getOrder().getPrice().getAmount())
                .createdAt(orderCreatedEvent.getLocalDateTime())
                .build();
    }

    public DriverRequestPayload orderRejectedEventToDriverRequestPayload(OrderRejectedEvent rejectedEvent) {
        return DriverRequestPayload.builder()
                .orderId(rejectedEvent.getOrder().getId().getValue().toString())
                .driverId(rejectedEvent.getOrder().getDriverId().toString())
                .position(orderAddressToStreetAddress(rejectedEvent.getOrder().getPosition()))
                .destination(orderAddressToStreetAddress(rejectedEvent.getOrder().getDestination()))
                .price(rejectedEvent.getOrder().getPrice().getAmount())
                .createdAt(rejectedEvent.getLocalDateTime())
                .build();
    }
    private PositionAddress orderAddressToStreetAddress(Position position) {
        return new PositionAddress(
                position.getStreetAddress(),
                position.getZipCode(),
                position.getCity()
        );
    }
    private Position positionAddressToPosition(PositionAddress positionAddress){
        return new Position(UUID.randomUUID(), positionAddress.getStreet(), positionAddress.getZipCode(), positionAddress.getCity());

    }

}
