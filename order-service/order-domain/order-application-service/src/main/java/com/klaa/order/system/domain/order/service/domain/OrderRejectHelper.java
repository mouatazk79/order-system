package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.order.service.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.order.service.domain.ports.output.publisher.reject.OrderRejectMessagePublisher;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class OrderRejectHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRejectMessagePublisher orderRejectMessagePublisher;
    private final OrderSaga orderSaga;
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final SagaHelper sagaHelper;


    @Transactional
    public OrderRejectedEvent rejectOrder(TrackOrderQuery trackOrderQuery) {
        Order order=sagaHelper.findOrderById(trackOrderQuery.getOrderTrackingId());
        List<String> failureMessages=new ArrayList<>();
        return orderDomainService.rejectOrderRequest(order,failureMessages,orderRejectMessagePublisher);
    }
}
