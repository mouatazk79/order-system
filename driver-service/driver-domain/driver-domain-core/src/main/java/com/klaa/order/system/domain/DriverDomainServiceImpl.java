package com.klaa.order.system.domain;

import com.klaa.order.system.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import java.time.LocalDateTime;
import java.util.List;
public class DriverDomainServiceImpl implements DriverDomainService {
    @Override
    public OrderDriverApprovalEvent validateAndApproveOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverRejectedEvent> orderDriverRejectedEventDomainEventPublisher) {
       orderApproval.validateOrder(failureMessages);
       if (failureMessages.isEmpty()){
           orderApproval.changeStatus(DriverOrderStatus.APPROVED);
           return new OrderDriverApprovedEvent(orderApproval,failureMessages, LocalDateTime.now(),orderDriverApprovedEventDomainEventPublisher);
       }else {
           return new OrderDriverRejectedEvent(orderApproval,failureMessages, LocalDateTime.now(),orderDriverRejectedEventDomainEventPublisher);
       }

    }

    @Override
    public OrderDriverApprovalEvent validateAndRejectOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverRejectedEvent> orderDriverRejectedEventDomainEventPublisher) {
       orderApproval.changeStatus(DriverOrderStatus.REJECTED);
       return new OrderDriverRejectedEvent(orderApproval,failureMessages, LocalDateTime.now(),orderDriverRejectedEventDomainEventPublisher);
    }
}
