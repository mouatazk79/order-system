package com.klaa.order.system.driver.service.domain.ports.input.service;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;

public interface DriverApplicationService {
DriverResponse approveOrder(DriverRequest driverRequest);
DriverResponse rejectOrder(DriverRequest driverRequest);
}
