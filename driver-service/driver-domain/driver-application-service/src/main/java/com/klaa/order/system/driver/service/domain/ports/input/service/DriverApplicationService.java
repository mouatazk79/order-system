package com.klaa.order.system.driver.service.domain.ports.input.service;

import com.klaa.order.system.driver.service.domain.dto.approval.ApprovalCommand;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;
import jakarta.validation.Valid;

public interface DriverApplicationService {
DriverResponse approveOrder(@Valid ApprovalCommand approvalCommand);
DriverResponse rejectOrder(@Valid ApprovalCommand approvalCommand);
}
