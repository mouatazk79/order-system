package com.klaa.order.system.driver.service.data.adapter;

import com.klaa.order.system.data.driver.repository.DriverJpaRepository;
import com.klaa.order.system.driver.service.data.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.domain.entity.Driver;
import com.klaa.order.system.driver.service.domain.ports.output.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
@AllArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final DriverJpaRepository driverJpaRepository;
    private final DriverDataMapper driverDataMapper;
    @Override
    public Optional<Driver> findDriverById(UUID id) {
        return driverDataMapper.driverEntityToDriver(driverJpaRepository.findById(id));
    }
}
