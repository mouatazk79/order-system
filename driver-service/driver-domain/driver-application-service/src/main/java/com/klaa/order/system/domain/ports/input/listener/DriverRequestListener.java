package com.klaa.order.system.domain.ports.input.listener;

import com.klaa.order.system.domain.dto.DriverRequest;

public interface DriverRequestListener {
    void orderRequest(DriverRequest driverRequest);
    void orderCancelled(DriverRequest driverRequest);
}
