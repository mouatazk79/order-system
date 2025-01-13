package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;


import java.util.List;

public interface DriverDomainService {
    OrderDriverApprovalEvent validateAndApproveOrder(OrderApproval orderApproval, List<String> failureMessages);
    OrderDriverApprovalEvent validateAndRejectOrder(OrderApproval orderApproval, List<String> failureMessages);
}
