package com.klaa.order.system.driver.service.data.outbox.adapter;

import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.driver.service.data.outbox.entity.OrderOutboxEntity;
import com.klaa.order.system.driver.service.data.outbox.exception.OrderOutboxNotFoundException;
import com.klaa.order.system.driver.service.data.outbox.mapper.OrderOutboxDataAccessMapper;
import com.klaa.order.system.driver.service.data.outbox.repository.OrderOutboxJpaRepository;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderOutboxRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Component
public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {

    private final OrderOutboxJpaRepository orderOutboxJpaRepository;
    private final OrderOutboxDataAccessMapper orderOutboxDataAccessMapper;

    public OrderOutboxRepositoryImpl(OrderOutboxJpaRepository orderOutboxJpaRepository,
                                     OrderOutboxDataAccessMapper orderOutboxDataAccessMapper) {
        this.orderOutboxJpaRepository = orderOutboxJpaRepository;
        this.orderOutboxDataAccessMapper = orderOutboxDataAccessMapper;
    }

    @Override
    public OrderOutboxMessage save(OrderOutboxMessage orderPaymentOutboxMessage) {
        log.info("OrderOutboxMessage : {}",orderPaymentOutboxMessage);
        OrderOutboxEntity orderOutboxEntity= orderOutboxJpaRepository.save(orderOutboxDataAccessMapper
                        .orderOutboxMessageToOrderOutboxEntity(orderPaymentOutboxMessage));
        log.info("OrderOutboxEntity : {}",orderOutboxEntity);
        return orderOutboxDataAccessMapper
                .orderOutboxEntityToOrderOutboxMessage(orderOutboxEntity);
    }

    @Override
    public Optional<List<OrderOutboxMessage>> findByTypeAndOutboxStatus(String sagaType, OutboxStatus outboxStatus) {
        return Optional.of(orderOutboxJpaRepository.findByTypeAndOutboxStatus(sagaType, outboxStatus)
                .orElseThrow(() -> new OrderOutboxNotFoundException("Approval outbox object " +
                        "cannot be found for saga type " + sagaType))
                .stream()
                .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<OrderOutboxMessage> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId,
                                                                           OutboxStatus outboxStatus) {
        return orderOutboxJpaRepository.findByTypeAndSagaIdAndOutboxStatus(type, sagaId, outboxStatus)
                .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage);
    }

    @Override
    public Optional<List<OrderOutboxMessage>> findByDriverOrderStatusNotAndOutboxStatus(DriverOrderStatus driverOrderStatus, OutboxStatus outboxStatus) {
        return Optional.of(orderOutboxJpaRepository.findByDriverOrderStatusNotAndOutboxStatus(driverOrderStatus,outboxStatus)
                .orElseThrow()
                .stream()
                .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage)
                .collect(Collectors.toList()));

    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        orderOutboxJpaRepository.deleteByTypeAndOutboxStatus(type, outboxStatus);
    }

    @Override
    public Optional<OrderOutboxMessage> findByTypeAndOrderIdAndOutboxStatus(UUID orderId, OutboxStatus outboxStatus) {
        return orderOutboxJpaRepository.findByOrderIdAndOutboxStatus(orderId, outboxStatus)
                .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage)

                ;
    }
}
