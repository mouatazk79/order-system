package com.klaa.order.system.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.OrderDomainService;
import com.klaa.order.system.order.service.domain.dto.message.DriverResponse;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.saga.SagaStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class OrderSaga implements SagaStep<DriverResponse> {
    private final OrderDomainService orderDomainService;
    private final SagaHelper sagaHelper;
    @Override
    @Transactional
    public void process(DriverResponse data) {
        log.info("receiving driver response with id: {}",data.getDriverId());
        Order order=sagaHelper.findOrderById(data.getOrderId());
        orderDomainService.approveOrder(order);
        sagaHelper.saveOrder(order);
    }

    @Override
    @Transactional
    public void rollBack(DriverResponse data) {
        log.info("rollback order with id: {}",data.getOrderId());
        Order order=sagaHelper.findOrderById(data.getOrderId());
        List<String> failureMessages=new ArrayList<>();
        orderDomainService.cancelOrder(order,failureMessages);
        sagaHelper.saveOrder(order);
    }
}
