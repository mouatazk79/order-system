package com.klaa.order.system.domain;

import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.event.OrderPaidEvent;
import com.klaa.order.system.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Driver driver, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);

    OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher);

    void cancelOrder(Order order, List<String> failureMessages);

    void rejectOrder(Order order,List<String> failureMessages);

    OrderRejectedEvent rejectOrderRequest(Order order, List<String> failureMessages,DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);


}
