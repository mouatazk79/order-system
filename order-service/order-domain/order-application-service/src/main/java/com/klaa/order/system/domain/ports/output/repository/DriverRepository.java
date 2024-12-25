package com.klaa.order.system.domain.ports.output.repository;

import com.klaa.order.system.domain.entity.Driver;

import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {
  Optional<Driver> findDriverById(UUID id);
}
