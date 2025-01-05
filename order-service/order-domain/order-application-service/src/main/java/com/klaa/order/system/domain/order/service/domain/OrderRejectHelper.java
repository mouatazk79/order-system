package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.order.service.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.order.service.domain.outbox.sheduler.driver.DriverOutboxHelper;
import com.klaa.order.system.domain.order.service.domain.ports.output.publisher.reject.OrderRejectMessagePublisher;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.OrderRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderRejectHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRejectMessagePublisher orderRejectMessagePublisher;
    private final OrderSaga orderSaga;
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final DriverOutboxHelper driverOutboxHelper;
    private final SagaHelper sagaHelper;


    @Transactional
    public OrderRejectedEvent rejectOrder(TrackOrderQuery trackOrderQuery) {
        Order order=sagaHelper.findOrderById(trackOrderQuery.getOrderTrackingId());
         List<String> failureMessages=new ArrayList<>();
        OrderRejectedEvent rejectedEvent=orderDomainService.rejectOrderRequest(order,failureMessages);
        driverOutboxHelper.saveDriverRequestOutboxMessage(
                orderDataMapper.orderRejectedEventToDriverRequestPayload(rejectedEvent),
                rejectedEvent.getOrder().getOrderStatus(),
                sagaHelper.orderStatusToSagaStatus(rejectedEvent.getOrder().getOrderStatus()),
                OutboxStatus.STARTED,
                UUID.randomUUID()
        );
        return rejectedEvent;
    }
}
