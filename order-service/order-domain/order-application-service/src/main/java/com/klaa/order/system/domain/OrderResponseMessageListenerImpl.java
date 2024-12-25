package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.message.DriverResponse;
import com.klaa.order.system.domain.ports.input.listener.driver.OrderResponseMessageListener;

public class OrderResponseMessageListenerImpl implements OrderResponseMessageListener {
    @Override
    public void orderApproved(DriverResponse driverResponse) {

    }

    @Override
    public void orderRejected(DriverResponse driverResponse) {

    }
}
