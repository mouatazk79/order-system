package com.klaa.order.system.order.service.data.order.adapter;

import com.klaa.order.system.order.service.data.order.mapper.OrderEntityMapper;
import com.klaa.order.system.order.service.data.order.repository.OrderJpaRepository;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;
    @Override
    public Optional<Order> saveOrder(Order order) {

        return orderJpaRepository.findById(order.getId().getValue()).map(orderEntityMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findOrderById(UUID id) {
        return Optional.empty();
    }
}
