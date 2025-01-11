package com.klaa.order.system.order.service.domain;

import com.klaa.order.system.order.service.domain.dto.message.DriverResponse;
import com.klaa.order.system.order.service.domain.ports.input.listener.driver.DriverOrderResponseMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class DriverOrderResponseMessageListenerImpl implements DriverOrderResponseMessageListener {
    private final OrderSaga orderSaga;
    @Override
    public void orderApproved(DriverResponse driverResponse) {
        orderSaga.process(driverResponse);
        log.info("Driver approved the order for order id: {}", driverResponse.getOrderId());
    }

    @Override
    public void orderRejected(DriverResponse driverResponse) {
        orderSaga.rollBack(driverResponse);
        log.info("Driver with id: {} rejected the order for order id: {}", driverResponse.getDriverId(),driverResponse.getOrderId());

    }
}
