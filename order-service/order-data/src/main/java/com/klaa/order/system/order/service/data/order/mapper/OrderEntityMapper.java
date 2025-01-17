package com.klaa.order.system.order.service.data.order.mapper;

import com.klaa.order.system.domain.order.service.domain.valueobjects.TrackingId;
import com.klaa.order.system.domain.valueobjects.*;
import com.klaa.order.system.order.service.data.order.entity.OrderEntity;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.order.service.data.order.entity.PositionAddress;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.klaa.order.system.domain.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;


@Component
public class OrderEntityMapper {
    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getOrderId()))
                .userId(new UserId(orderEntity.getUserId()))
                .position(positionAddressToPosition(orderEntity.getPosition()))
                .destination(positionAddressToPosition(orderEntity.getDestination()))
                .price(new Money(orderEntity.getPrice()))
                .driverId(new DriverId(orderEntity.getDriverId()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .build();
    }
    public OrderEntity orderToOrderEntity(Order order){

        return OrderEntity.builder()
                .orderId(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .position(new PositionAddress(UUID.randomUUID(),order.getPosition().getStreetAddress(),order.getPosition().getZipCode(),order.getPosition().getZipCode()))
                .destination(new PositionAddress(UUID.randomUUID(),order.getDestination().getStreetAddress(),order.getDestination().getZipCode(),order.getDestination().getZipCode()))
                .price(order.getPrice().getAmount())
                .driverId(order.getDriverId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private Position positionAddressToPosition(PositionAddress positionAddress){
        return new Position(UUID.randomUUID(), positionAddress.getStreetAddress(), positionAddress.getZipCode(), positionAddress.getCity());

    }
}
