package com.klaa.order.system.data.driver.adapter;

import com.klaa.order.system.data.driver.repository.DriverJpaRepository;
import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.ports.output.repository.DriverRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;
@AllArgsConstructor
public class DriverRepositoryAdapter implements DriverRepository {
    private final DriverJpaRepository driverJpaRepository;
    @Override
    public Optional<Driver> findDriverById(UUID id) {
        return Optional.empty();
    }
}
