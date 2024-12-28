package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.entity.Driver;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.order.service.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.order.service.domain.event.OrderPaidEvent;
import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.driver.service.domain.event.publisher.DomainEventPublisher;

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
