package com.klaa.order.system.domain;

import com.klaa.order.system.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.domain.event.publisher.DomainEventPublisher;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
public class DriverDomainServiceImpl implements DriverDomainService {
    @Override
    public OrderDriverApprovalEvent validateAndApproveOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverFailedEvent> orderDriverFailedEventDomainEventPublisher) {
       orderApproval.validateOrder(failureMessages);
       if (failureMessages.isEmpty()){
           log.info("order with id: {} approved",orderApproval.getId());
           orderApproval.changeStatus(DriverOrderStatus.APPROVED);
           return new OrderDriverApprovedEvent(orderApproval,failureMessages, LocalDateTime.now(),orderDriverApprovedEventDomainEventPublisher);
       }else {
           log.info("order with id: {} failed",orderApproval.getId());
           return new OrderDriverFailedEvent(orderApproval,failureMessages, LocalDateTime.now(),orderDriverFailedEventDomainEventPublisher);
       }

    }

    @Override
    public OrderDriverApprovalEvent validateAndRejectOrder(OrderApproval orderApproval, List<String> failureMessages, DomainEventPublisher<OrderDriverApprovedEvent> orderDriverApprovedEventDomainEventPublisher, DomainEventPublisher<OrderDriverRejectedEvent> orderDriverRejectedEventDomainEventPublisher) {
        log.info("order with id: {} rejected",orderApproval.getId());
       orderApproval.changeStatus(DriverOrderStatus.REJECTED);
       return new OrderDriverRejectedEvent(orderApproval,failureMessages, LocalDateTime.now(),orderDriverRejectedEventDomainEventPublisher);
    }
}
