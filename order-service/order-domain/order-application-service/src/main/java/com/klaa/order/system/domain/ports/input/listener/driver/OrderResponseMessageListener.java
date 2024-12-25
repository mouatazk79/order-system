package com.klaa.order.system.domain.ports.input.listener.driver;

import com.klaa.order.system.domain.dto.message.DriverResponse;

public interface OrderResponseMessageListener {
    void orderApproved(DriverResponse driverResponse);

    void orderRejected(DriverResponse driverResponse);
}
