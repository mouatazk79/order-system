package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.entity.Driver;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.event.*;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Driver driver);

    void payOrder(Order order);

    OrderApprovedEvent approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);

    OrderRejectedEvent rejectOrderRequest(Order order, List<String> failureMessages);


}
