package com.klaa.order.system.domain.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> saveOrder(Order order);
    Optional<Order> findOrderById(UUID id);

}
