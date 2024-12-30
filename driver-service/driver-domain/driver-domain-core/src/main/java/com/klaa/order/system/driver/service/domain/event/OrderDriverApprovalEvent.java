package com.klaa.order.system.driver.service.domain.event;

import com.klaa.order.system.domain.event.DomainEvent;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;

import java.time.LocalDateTime;
import java.util.List;

public abstract class OrderDriverApprovalEvent implements DomainEvent<OrderApproval> {
    private final OrderApproval orderApproval;
    private final List<String> failureMessages;
    private final LocalDateTime localDateTime;


    protected OrderDriverApprovalEvent(OrderApproval orderApproval, List<String> failureMessages, LocalDateTime localDateTime) {
        this.orderApproval = orderApproval;
        this.failureMessages = failureMessages;
        this.localDateTime = localDateTime;
    }

    public OrderApproval getOrderApproval() {
        return orderApproval;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
