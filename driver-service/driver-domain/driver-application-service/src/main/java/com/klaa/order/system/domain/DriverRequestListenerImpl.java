package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.DriverRequest;
import com.klaa.order.system.domain.ports.input.listener.DriverRequestListener;

public class DriverRequestListenerImpl implements DriverRequestListener {
    @Override
    public void orderRequest(DriverRequest driverRequest) {


    }

    @Override
    public void orderCancelled(DriverRequest driverRequest) {

    }
}
