package com.klaa.order.system.driver.service.data.mapper;

import com.klaa.order.system.data.driver.entity.DriverEntity;
import com.klaa.order.system.driver.service.data.entity.OrderApprovalEntity;
import com.klaa.order.system.driver.service.domain.entity.Driver;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class DriverDataMapper {
    public Optional<Driver> driverEntityToDriver(Optional<DriverEntity> byId) {
        return null;
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return null;
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return null;
    }
}
