package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.reject.RejectOrderResponse;
import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.order.service.domain.event.OrderRejectedEvent;
import com.klaa.order.system.domain.order.service.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.order.service.domain.outbox.sheduler.driver.DriverOutboxHelper;
import com.klaa.order.system.domain.order.service.domain.ports.output.publisher.reject.OrderRejectMessagePublisher;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderRejectHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRejectHelper orderRejectHelper;


    public RejectOrderResponse rejectOrder(TrackOrderQuery trackOrderQuery) {
       OrderRejectedEvent rejectedEvent= orderRejectHelper.rejectOrder(trackOrderQuery);
       log.info("order with id: {} rejected",trackOrderQuery.getOrderTrackingId());
       return orderDataMapper.orderRejectedEventToRejectOrderResponse(rejectedEvent);
    }
}
