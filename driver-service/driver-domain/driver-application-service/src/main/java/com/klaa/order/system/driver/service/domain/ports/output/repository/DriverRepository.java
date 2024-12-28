package com.klaa.order.system.driver.service.domain.ports.output.repository;

import com.klaa.order.system.driver.service.domain.entity.Driver;

import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {
    Optional<Driver> findDriverById(UUID id);
}
