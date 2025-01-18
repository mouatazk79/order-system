package com.klaa.order.system.data.driver.repository;

import com.klaa.order.system.data.driver.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface DriverJpaRepository extends JpaRepository<DriverEntity, UUID> {
 Optional<DriverEntity> findByDriverId(UUID driverId);
}
