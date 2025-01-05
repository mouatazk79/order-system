package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.message.PaymentResponse;
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
public class PaymentSaga implements SagaStep<PaymentResponse> {
    private final OrderDomainService orderDomainService;
    private final SagaHelper sagaHelper;

    @Override
    @Transactional
    public void process(PaymentResponse data) {
        log.info("receiving payment response with id: {}",data.getPaymentId());
        Order order=sagaHelper.findOrderById(data.getOrderId());
        orderDomainService.payOrder(order);
        sagaHelper.saveOrder(order);
    }

    @Override
    @Transactional
    public void rollBack(PaymentResponse data) {
        log.info("rollback payment response with id: {}",data.getPaymentId());
        Order order=sagaHelper.findOrderById(data.getOrderId());
        List<String> failureMessages=new ArrayList<>();
        orderDomainService.cancelOrderPayment(order,failureMessages);
        sagaHelper.saveOrder(order);
    }
}
