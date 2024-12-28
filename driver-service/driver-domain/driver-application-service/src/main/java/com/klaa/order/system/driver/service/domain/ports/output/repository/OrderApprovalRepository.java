package com.klaa.order.system.driver.service.domain.ports.output.repository;

import com.klaa.order.system.driver.service.domain.entity.OrderApproval;

import java.util.Optional;

public interface OrderApprovalRepository {

    Optional<OrderApproval> orderApproval(OrderApproval orderApproval);
}
