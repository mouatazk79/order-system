package com.klaa.order.system.driver.service.domain.ports.input.service;

import com.klaa.order.system.driver.service.domain.dto.approval.ApprovalCommand;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;

public interface DriverApplicationService {
DriverResponse approveOrder(ApprovalCommand approvalCommand);
DriverResponse rejectOrder(ApprovalCommand approvalCommand);
}
