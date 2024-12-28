package com.klaa.order.system.driver.service.domain.ports.output.repository;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;

import java.util.Optional;
import java.util.UUID;

public interface OrderApprovalRepository {

    Optional<OrderApproval> saveOrderApproval(OrderApproval orderApproval);
    Optional<OrderApproval> findOrderId(UUID id);

}
