package com.klaa.order.system.domain;

import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.event.OrderPaidEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Driver driver, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher) {
        return null;
    }

    @Override
    public OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher) {
        return null;
    }

    @Override
    public void approveOrder(Order order) {

    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher) {
        return null;
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {

    }
}
