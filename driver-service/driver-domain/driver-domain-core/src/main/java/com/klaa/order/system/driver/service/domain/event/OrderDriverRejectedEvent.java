package com.klaa.order.system.driver.service.domain.event;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDriverRejectedEvent extends OrderDriverApprovalEvent{

    public OrderDriverRejectedEvent(OrderApproval orderApproval, List<String> failureMessages, LocalDateTime localDateTime) {
        super(orderApproval, failureMessages, localDateTime);
    }

}
