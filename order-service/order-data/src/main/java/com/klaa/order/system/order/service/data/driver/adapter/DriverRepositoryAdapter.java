package com.klaa.order.system.order.service.data.driver.adapter;

import com.klaa.order.system.data.driver.repository.DriverJpaRepository;
import com.klaa.order.system.domain.order.service.domain.entity.Driver;
import com.klaa.order.system.order.service.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.order.service.data.driver.mapper.DriverEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
@AllArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final DriverJpaRepository driverJpaRepository;
    private final DriverEntityMapper driverEntityMapper;
    @Override
    public Optional<Driver> findDriverById(UUID id) {
        return Optional.of(driverEntityMapper.driverEntityToDriver(driverJpaRepository.findById(id).get()));
    }
}
