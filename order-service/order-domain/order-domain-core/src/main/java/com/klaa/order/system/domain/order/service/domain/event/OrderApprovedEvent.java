package com.klaa.order.system.domain.order.service.domain.event;

import com.klaa.order.system.domain.order.service.domain.entity.Order;

import java.time.LocalDateTime;

public class OrderApprovedEvent extends OrderEvent{

    public OrderApprovedEvent(Order order, LocalDateTime localDateTime) {
        super(order, localDateTime);
    }


}
