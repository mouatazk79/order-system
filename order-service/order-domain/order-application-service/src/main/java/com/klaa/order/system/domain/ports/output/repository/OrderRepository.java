package com.klaa.order.system.domain.ports.output.repository;

import com.klaa.order.system.domain.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> saveOrder(Order order);
    Optional<Order> findOrderById(UUID id);

}
