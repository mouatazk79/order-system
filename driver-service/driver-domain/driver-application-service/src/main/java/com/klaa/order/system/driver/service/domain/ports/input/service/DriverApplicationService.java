package com.klaa.order.system.driver.service.domain.ports.input.service;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.reject.DriverRejectResponse;

public interface DriverApplicationService {
DriverRejectResponse rejectOrder(DriverRequest driverRequest);
}
