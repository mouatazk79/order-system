package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.message.DriverResponse;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.EmptyEvent;
import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.saga.SagaStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Slf4j
@AllArgsConstructor
public class OrderSaga implements SagaStep<DriverResponse, OrderCreatedEvent, OrderCancelledEvent> {
    private final OrderDomainService orderDomainService;
    private final SagaHelper sagaHelper;
    @Override
    @Transactional
    public OrderCreatedEvent process(DriverResponse data) {
        log.info("receiving driver response with id: {}",data.getDriverId());
        Order order=sagaHelper.findOrderById(data.getOrderId());
        orderDomainService.approveOrder(order);
        sagaHelper.saveOrder(order);
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCancelledEvent rollBack(DriverResponse data) {
        return null;
    }
}
