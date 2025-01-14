package com.klaa.order.system.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.OrderDomainService;
import com.klaa.order.system.domain.order.service.domain.event.OrderApprovedEvent;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.domain.valueobjects.OrderStatus;
import com.klaa.order.system.order.service.domain.dto.message.DriverResponse;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.order.service.domain.mapper.OrderDataMapper;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
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

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class OrderSaga implements SagaStep<DriverResponse> {
    private final OrderDomainService orderDomainService;
    private final DriverOutboxHelper driverOutboxHelper;
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final SagaHelper sagaHelper;
    @Override
    @Transactional
    public void process(DriverResponse driverResponse) {
        log.info("receiving driver response with id: {}",driverResponse.getDriverId());
        Optional<DriverRequestOutboxMessage> driverRequestOutboxMessageResponse=driverOutboxHelper
                .getDriverRequestOutboxMessageBySagaIdAndSagaStatus(UUID.fromString(driverResponse.getSagaId()), SagaStatus.STARTED);
        if (driverRequestOutboxMessageResponse.isEmpty()){
            log.info("An outbox message with saga id: {} is already processed!", driverResponse.getSagaId());
            return;
        }
        OrderApprovedEvent approvedEvent=approveOrder(driverResponse);
        DriverRequestOutboxMessage driverRequestOutboxMessage=driverRequestOutboxMessageResponse.get();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(approvedEvent.getOrder().getOrderStatus());
        driverOutboxHelper.save(updateDriverRequestOutboxMessage(driverRequestOutboxMessage,approvedEvent.getOrder().getOrderStatus(), sagaStatus));
        paymentOutboxHelper.savePaymentOutboxMessage(
                orderDataMapper.orderApprovedEventToPaymentRequestPayload(approvedEvent),
                approvedEvent.getOrder().getOrderStatus(),
                sagaStatus,
                OutboxStatus.STARTED,
                UUID.fromString(driverResponse.getSagaId()));
        log.info("Order with id: {} is paid", approvedEvent.getOrder().getId().getValue());

    }

    @Override
    @Transactional
    public void rollBack(DriverResponse driverResponse) {
        log.info("rollback order with id: {}",driverResponse.getOrderId());

        Optional<DriverRequestOutboxMessage> driverRequestOutboxMessageResponse =
                driverOutboxHelper.getDriverRequestOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(driverResponse.getSagaId()),
                        getCurrentSagaStatus(driverResponse.getDriverOrderStatus()));
        if (driverRequestOutboxMessageResponse.isEmpty()){
            log.info("An outbox message with saga id: {} is already processed!", driverResponse.getSagaId());
            return;
        }
        Order order=rejectOrder(driverResponse);
        DriverRequestOutboxMessage driverRequestOutboxMessage=driverRequestOutboxMessageResponse.get();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(order.getOrderStatus());
        driverOutboxHelper.save(updateDriverRequestOutboxMessage(driverRequestOutboxMessage,order.getOrderStatus(), sagaStatus));
    }
    private OrderApprovedEvent approveOrder(DriverResponse driverResponse){
        Order order=sagaHelper.findOrderById(driverResponse.getOrderId());
        OrderApprovedEvent approvedEvent=orderDomainService.approveOrder(order);
        orderRepository.saveOrder(order);
        return approvedEvent;
    }
    private DriverRequestOutboxMessage updateDriverRequestOutboxMessage(DriverRequestOutboxMessage driverRequestOutboxMessage, OrderStatus orderStatus,SagaStatus sagaStatus){
        driverRequestOutboxMessage.setSagaStatus(sagaStatus);
        driverRequestOutboxMessage.setOrderStatus(orderStatus);
        driverRequestOutboxMessage.setProcessedAt(LocalDateTime.now());
        return driverRequestOutboxMessage;
    }
    private SagaStatus getCurrentSagaStatus(DriverOrderStatus driverOrderStatus){
       return switch (driverOrderStatus){
           case PENDING -> null;
           case APPROVED -> SagaStatus.valueOf(SagaStatus.STARTED.name());
            case REJECTED -> SagaStatus.valueOf(SagaStatus.FAILED.name());
        };
    }

    private Order rejectOrder(DriverResponse driverResponse){
        Order order=sagaHelper.findOrderById(driverResponse.getOrderId());
        orderDomainService.cancelOrder(order,driverResponse.getFailureMessages());
        orderRepository.saveOrder(order);
        return order;
    }

}
