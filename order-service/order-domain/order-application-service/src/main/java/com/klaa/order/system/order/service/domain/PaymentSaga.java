package com.klaa.order.system.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.OrderDomainService;
import com.klaa.order.system.domain.order.service.domain.event.OrderCancelledEvent;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.domain.valueobjects.OrderStatus;
import com.klaa.order.system.order.service.domain.dto.message.PaymentResponse;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.order.service.domain.outbox.model.payment.PaymentRequestOutboxMessage;
import com.klaa.order.system.order.service.domain.outbox.sheduler.driver.DriverOutboxHelper;
import com.klaa.order.system.order.service.domain.outbox.sheduler.payment.PaymentOutboxHelper;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import com.klaa.order.system.saga.SagaStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class PaymentSaga implements SagaStep<PaymentResponse> {
    private final OrderDomainService orderDomainService;
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final DriverOutboxHelper driverOutboxHelper;
    private final OrderRepository orderRepository;
    private final SagaHelper sagaHelper;

    @Override
    @Transactional
    public void process(PaymentResponse paymentResponse) {
        log.info("receiving payment response with id: {}",paymentResponse.getPaymentId());
        Optional<PaymentRequestOutboxMessage> paymentRequestOutboxMessageResponse=paymentOutboxHelper
                .getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID.fromString(paymentResponse.getSagaId()), SagaStatus.PROCESSING);
        if (paymentRequestOutboxMessageResponse.isEmpty()){
            log.info("An outbox message with saga id: {} is failed!", paymentResponse.getSagaId());
            return;
        }
        Order order=payOrder(paymentResponse);
        PaymentRequestOutboxMessage paymentRequestOutboxMessage=paymentRequestOutboxMessageResponse.get();
        SagaStatus sagaStatus =sagaHelper.orderStatusToSagaStatus(order.getOrderStatus());
        paymentOutboxHelper.save(updatePaymentRequestOutboxMessage(paymentRequestOutboxMessage,order.getOrderStatus(),sagaStatus, OutboxStatus.COMPLETED));
        driverOutboxHelper.save(getUpdatedDriverRequestOutboxMessage(paymentResponse.getSagaId(),order.getOrderStatus(),sagaStatus,OutboxStatus.COMPLETED));
        log.info("Order with id: {} is paid ", order.getId());

    }



    @Override
    @Transactional
    public void rollBack(PaymentResponse paymentResponse) {
        log.info("rollback payment response with id: {}",paymentResponse.getPaymentId());
        Optional<PaymentRequestOutboxMessage> paymentRequestOutboxMessageResponse=paymentOutboxHelper
                .getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID.fromString(paymentResponse.getSagaId()), SagaStatus.PROCESSING);
        if (paymentRequestOutboxMessageResponse.isEmpty()){
            log.info("An outbox message with saga id: {} is failed!", paymentResponse.getSagaId());
            return;
        }
        PaymentRequestOutboxMessage paymentRequestOutboxMessage=paymentRequestOutboxMessageResponse.get();
        OrderCancelledEvent orderCancelledEvent=cancelOrder(paymentResponse);
        SagaStatus sagaStatus =sagaHelper.orderStatusToSagaStatus(orderCancelledEvent.getOrder().getOrderStatus());
        paymentOutboxHelper.save(updatePaymentRequestOutboxMessage(paymentRequestOutboxMessage,orderCancelledEvent.getOrder().getOrderStatus(),sagaStatus, OutboxStatus.FAILED));
        driverOutboxHelper.save(getUpdatedDriverRequestOutboxMessage(paymentResponse.getSagaId(),orderCancelledEvent.getOrder().getOrderStatus(),sagaStatus,OutboxStatus.FAILED));
        log.info("Order with id: {} is canceled ", orderCancelledEvent.getOrder().getId());

    }
    private DriverRequestOutboxMessage getUpdatedDriverRequestOutboxMessage(String sagaId,
                                                                     OrderStatus orderStatus,
                                                                     SagaStatus sagaStatus, OutboxStatus outboxStatus
    ) {
        Optional<DriverRequestOutboxMessage> driverRequestOutboxMessageResponse = driverOutboxHelper
                .getDriverRequestOutboxMessageBySagaIdAndSagaStatus(UUID.fromString(sagaId), SagaStatus.PROCESSING);
        if (driverRequestOutboxMessageResponse.isEmpty()) {
            throw new OrderDomainException("driver outbox message cannot be found in " +
                    SagaStatus.PROCESSING.name() + " state");
        }
        DriverRequestOutboxMessage driverRequestOutboxMessage = driverRequestOutboxMessageResponse.get();
        driverRequestOutboxMessage.setProcessedAt(LocalDateTime.now());
        driverRequestOutboxMessage.setOrderStatus(orderStatus);
        driverRequestOutboxMessage.setSagaStatus(sagaStatus);
        driverRequestOutboxMessage.setOutboxStatus(outboxStatus);
        return driverRequestOutboxMessage;
    }
    private PaymentRequestOutboxMessage updatePaymentRequestOutboxMessage(PaymentRequestOutboxMessage paymentRequestOutboxMessage, OrderStatus orderStatus, SagaStatus sagaStatus, OutboxStatus outboxStatus) {
        paymentRequestOutboxMessage.setOrderStatus(orderStatus);
        paymentRequestOutboxMessage.setSagaStatus(sagaStatus);
        paymentRequestOutboxMessage.setOutboxStatus(outboxStatus);
        paymentRequestOutboxMessage.setProcessedAt(LocalDateTime.now());
        return paymentRequestOutboxMessage;
    }
    private Order payOrder(PaymentResponse paymentResponse){
        Order order=sagaHelper.findOrderById(paymentResponse.getOrderId());
        orderDomainService.payOrder(order);
        orderRepository.saveOrder(order);
        return order;
    }

    private OrderCancelledEvent cancelOrder(PaymentResponse paymentResponse){
        Order order=sagaHelper.findOrderById(paymentResponse.getOrderId());
        List<String> failureMessages=new ArrayList<>();
        OrderCancelledEvent orderCancelledEvent=orderDomainService.cancelOrderPayment(order,failureMessages);
        sagaHelper.saveOrder(order);
        return orderCancelledEvent;
    }
}
