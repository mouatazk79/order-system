package com.klaa.order.system.order.service.domain.ports.input.listener.driver;

import com.klaa.order.system.order.service.domain.dto.message.DriverResponse;

public interface DriverOrderResponseMessageListener {
    void orderApproved(DriverResponse driverResponse);

    void orderRejected(DriverResponse driverResponse);
}
