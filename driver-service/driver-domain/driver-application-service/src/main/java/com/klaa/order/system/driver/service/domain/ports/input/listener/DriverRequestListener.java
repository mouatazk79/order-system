package com.klaa.order.system.driver.service.domain.ports.input.listener;


import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;

public interface DriverRequestListener {
    void orderRequest(DriverRequest driverRequest);
    void orderCancelled(DriverRequest driverRequest);
}
