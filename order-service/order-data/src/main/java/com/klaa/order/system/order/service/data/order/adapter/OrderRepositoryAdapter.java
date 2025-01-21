package com.klaa.order.system.order.service.data.order.adapter;

import com.klaa.order.system.order.service.data.order.mapper.OrderEntityMapper;
import com.klaa.order.system.order.service.data.order.repository.OrderJpaRepository;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
@AllArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;
    @Override
    public Optional<Order> saveOrder(Order order) {
        return Optional.of(orderEntityMapper.orderEntityToOrder(orderJpaRepository.save(orderEntityMapper.orderToOrderEntity(order))));
    }

    @Override
    public Optional<Order> findOrderById(UUID id) {
        return orderJpaRepository.findByOrderId(id).map(orderEntityMapper::orderEntityToOrder);
    }
    @Override
    public Optional<Order> findOrderByTrackingId(UUID id) {
        return orderJpaRepository.findByTrackingId(id).map(orderEntityMapper::orderEntityToOrder);
    }

    @Override
    public List<Order> findOrdersCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return orderJpaRepository.findByCreatedDateIsBetween(startTime,endTime)
                .stream()
                .map(orderEntityMapper::orderEntityToOrder).toList();
    }
}
