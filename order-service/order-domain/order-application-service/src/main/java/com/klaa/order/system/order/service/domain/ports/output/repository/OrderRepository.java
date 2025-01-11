package com.klaa.order.system.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> saveOrder(Order order);
    Optional<Order> findOrderById(UUID id);
    List<Order> findOrdersCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

}
