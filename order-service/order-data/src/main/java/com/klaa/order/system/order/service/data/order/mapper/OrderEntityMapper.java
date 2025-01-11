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
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages()
                                .split(FAILURE_MESSAGE_DELIMITER)))).build();
    }

    private Position positionAddressToPosition(PositionAddress positionAddress){
        return new Position(UUID.randomUUID(), positionAddress.getStreetAddress(), positionAddress.getZipCode(), positionAddress.getCity());

    }
}
