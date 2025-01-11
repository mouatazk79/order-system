package com.klaa.order.system.order.service.data.driver.mapper;

import com.klaa.order.system.data.driver.entity.DriverEntity;
import com.klaa.order.system.domain.order.service.domain.entity.Driver;
import com.klaa.order.system.domain.valueobjects.DriverId;
import org.springframework.stereotype.Component;


@Component
public class DriverEntityMapper {
    public Driver driverEntityToDriver(DriverEntity driverEntity) {
        return Driver.builder()
                .driverId(new DriverId(driverEntity.getDriverId()))
                .car(driverEntity.getCar())
                .active(driverEntity.getActive())
                .build();
    }
}
