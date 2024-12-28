package com.klaa.order.system.domain.order.service.domain.ports.input.listener.driver;

import com.klaa.order.system.domain.order.service.domain.dto.message.DriverResponse;

public interface OrderResponseMessageListener {
    void orderApproved(DriverResponse driverResponse);

    void orderRejected(DriverResponse driverResponse);
}
