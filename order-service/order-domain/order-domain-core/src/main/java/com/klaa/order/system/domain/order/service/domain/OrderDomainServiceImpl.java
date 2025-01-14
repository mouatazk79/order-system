package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.entity.Driver;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.event.*;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Driver driver) {

        validateDriver(driver);
        order.validateOrder();
        order.initializeOrder(driver.getId());
        log.info("Order with id: {} initiated", order.getId());
        return new OrderCreatedEvent(order, LocalDateTime.now());
    }

    @Override
    public void payOrder(Order order ) {
        order.pay();
        log.info("Order with id: {} payed", order.getId());
    }

    @Override
    public OrderApprovedEvent approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} approved", order.getId());
        return new OrderApprovedEvent(order,LocalDateTime.now());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {

        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId());
        return new OrderCancelledEvent(order,LocalDateTime.now());
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order cancelled for order id: {}", order.getId());
    }


    @Override
    public OrderRejectedEvent rejectOrderRequest(Order order, List<String> failureMessages ) {
        order.initReject(failureMessages);
        log.info("Order rejecting for order id: {}", order.getId());
        return new OrderRejectedEvent(order,LocalDateTime.now());
    }

    private void validateDriver(Driver driver){
        if (!driver.isActive()){
            throw new OrderDomainException("driver with id: "+driver.getId()+" is not active");
        }
    }
}
