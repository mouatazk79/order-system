package com.klaa.order.system.domain.ports.output.repository;

import com.klaa.order.system.domain.entity.OrderApproval;

import java.util.Optional;

public interface OrderApprovalRepository {

    Optional<OrderApproval> orderApproval(OrderApproval orderApproval);
}
