package com.klaa.order.system.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.entity.Driver;

import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {
  Optional<Driver> findDriverById(UUID id);
}
