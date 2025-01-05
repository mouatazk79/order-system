package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.ports.input.listener.DriverRequestListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DriverRequestListenerImpl implements DriverRequestListener {
    private final DriverRequestHelper driverRequestHelper;


    @Override
    public void orderRequest(DriverRequest driverRequest) {
         driverRequestHelper.persistDriverRequest(driverRequest);
    }

    @Override
    public void orderCancelled(DriverRequest driverRequest) {
        driverRequestHelper.persistCancelPayment(driverRequest);
    }



}
