package com.klaa.order.system.order.service.data.order.repository;

import com.klaa.order.system.order.service.data.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}
