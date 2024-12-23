package com.klaa.order.system.domain;

import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.event.OrderPaidEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;
import com.klaa.order.system.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Driver driver, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher) {

        validateDriver(driver);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} initiated", order.getId());
        return new OrderCreatedEvent(order, LocalDateTime.now(),orderCreatedEventDomainEventPublisher);
    }

    @Override
    public OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher) {
        order.pay();
        log.info("Order with id: {} payed", order.getId());
        return new OrderPaidEvent(order, LocalDateTime.now(),orderPaidEventDomainEventPublisher);
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} approved", order.getId());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher) {

        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId());
        return new OrderCancelledEvent(order,LocalDateTime.now(),orderCancelledEventDomainEventPublisher);
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order cancelled for order id: {}", order.getId());
    }
    private void validateDriver(Driver driver){
        if (!driver.isActive()){
            throw new OrderDomainException("driver with id: "+driver.getId()+" is not active");
        }
    }
}
