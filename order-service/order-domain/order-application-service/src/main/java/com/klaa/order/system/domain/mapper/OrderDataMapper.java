package com.klaa.order.system.domain.mapper;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.dto.create.PositionAddress;
import com.klaa.order.system.domain.dto.remove.RejectOrderResponse;
import com.klaa.order.system.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.Position;
import com.klaa.order.system.domain.valueobjects.UserId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderDataMapper {

    public Order  orderCreateCommandToOrder(OrderCreateCommand orderCreateCommand){
        return Order.builder()
                .userId(new UserId(orderCreateCommand.getCustomerId()))
                .position(positionAddressToPosition(orderCreateCommand.getPosition()))
                .destination(positionAddressToPosition(orderCreateCommand.getDestinationAddress()))
                .price(new Money(orderCreateCommand.getPrice()))
                .build();
    }

    public Position positionAddressToPosition(PositionAddress positionAddress){
        return new Position(UUID.randomUUID(), positionAddress.getStreet(), positionAddress.getZipCode(), positionAddress.getCity());

    }

    // TODO: 12/25/2024 this last two methods will be modified
    public OrderCreateResponse orderCreatedEventToOrderCreateResponse(OrderCreatedEvent orderCreatedEvent) {
        return OrderCreateResponse.builder()
                .orderStatus(orderCreatedEvent.getOrder().getOrderStatus())
                .orderTrackingId(orderCreatedEvent.getOrder().getTrackingId().getValue())
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(TrackOrderQuery trackOrderQuery) {
        return TrackOrderResponse.builder()
                .orderTrackingId(trackOrderQuery.getOrderTrackingId())
                .build();
    }

    public RejectOrderResponse orderRejectedEventToRejectOrderResponse(OrderRejectedEvent rejectedEvent) {
        return null;
    }
}
