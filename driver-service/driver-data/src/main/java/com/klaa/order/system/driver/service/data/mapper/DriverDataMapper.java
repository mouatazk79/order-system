package com.klaa.order.system.driver.service.data.mapper;

import com.klaa.order.system.data.driver.entity.DriverEntity;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.driver.service.data.entity.OrderApprovalEntity;
import com.klaa.order.system.driver.service.domain.entity.Driver;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.entity.OrderDetail;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class DriverDataMapper {
    public Optional<Driver> driverEntityToDriver(Optional<DriverEntity> byId) {
        return null;
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .orderApprovalId(orderApproval.getId().getValue())
                .driverId(orderApproval.getDriverId().getValue())
                .orderId(orderApproval.getOrderDetail().getId().getValue())
                .driverOrderStatus(orderApproval.getOrderStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .driverId(new DriverId( orderApprovalEntity.getDriverId()))
                .orderDetail(OrderDetail.builder()
//                        .orderId(orderApprovalEntity.getOrderId())
//                        .position(orderApprovalEntity.get)
                        .build())
                .driverOrderStatus(orderApprovalEntity.getDriverOrderStatus())
                .build();
    }
}
