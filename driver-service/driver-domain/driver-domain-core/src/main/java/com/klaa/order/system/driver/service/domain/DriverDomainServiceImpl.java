package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovedEvent;
import com.klaa.order.system.driver.service.domain.event.OrderDriverFailedEvent;
import com.klaa.order.system.driver.service.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
public class DriverDomainServiceImpl implements DriverDomainService {
    @Override
    public OrderDriverApprovalEvent validateAndApproveOrder(OrderApproval orderApproval, List<String> failureMessages) {
       orderApproval.validateOrder(failureMessages);
       if (failureMessages.isEmpty()){
           log.info("order with id: {} approved",orderApproval.getId());
           orderApproval.changeStatus(DriverOrderStatus.APPROVED);
           return new OrderDriverApprovedEvent(orderApproval,failureMessages, LocalDateTime.now());
       }else {
           log.info("order with id: {} failed",orderApproval.getId());
           return new OrderDriverFailedEvent(orderApproval,failureMessages, LocalDateTime.now());
       }

    }

    @Override
    public OrderDriverApprovalEvent validateAndRejectOrder(OrderApproval orderApproval, List<String> failureMessages) {
        orderApproval.validateOrder(failureMessages);
        if (failureMessages.isEmpty()){
            log.info("order with id: {} rejected",orderApproval.getId());
            orderApproval.changeStatus(DriverOrderStatus.REJECTED);
            return new OrderDriverRejectedEvent(orderApproval,failureMessages, LocalDateTime.now());
        }else {
            log.info("order with id: {} failed",orderApproval.getId());
            return new OrderDriverFailedEvent(orderApproval,failureMessages, LocalDateTime.now());
        }
    }
}
