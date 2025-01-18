package com.klaa.order.system.driver.service.data.mapper;

import com.klaa.order.system.data.driver.entity.DriverEntity;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.driver.service.data.entity.OrderApprovalEntity;
import com.klaa.order.system.driver.service.domain.entity.Driver;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.entity.OrderDetail;
import com.klaa.order.system.driver.service.domain.valueobject.OrderApprovalId;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class DriverDataMapper {
    public Optional<Driver> driverEntityToDriver(Optional<DriverEntity> driverEntity) {
        return Optional.of(Driver.builder()
                .driverId(new DriverId(driverEntity.get().getDriverId()))
                .firstName(driverEntity.get().getFirstName())
                .lastName(driverEntity.get().getLastName())
                .email(driverEntity.get().getEmail())
                .car(driverEntity.get().getCar())
                .phoneNumber(driverEntity.get().getPhoneNumber())
                .active(driverEntity.get().getActive())
                .build());
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .orderApprovalId(orderApproval.getId().getValue())
                .driverId(orderApproval.getDriverId().getValue())
                .orderId(orderApproval.getOrderDetail().getId().getValue())
                .driverOrderStatus(orderApproval.getDriverOrderStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getOrderApprovalId()))
                .driverId(new DriverId( orderApprovalEntity.getDriverId()))
                .orderDetail(OrderDetail.builder().orderId(new OrderId(orderApprovalEntity.getOrderId())).build())
                .driverOrderStatus(orderApprovalEntity.getDriverOrderStatus())
                .build();
    }
}
