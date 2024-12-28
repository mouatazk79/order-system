package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.order.service.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.order.service.domain.dto.reject.DriverRejectOrderResponse;
import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.domain.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;
    private final OrderRejectHandler orderRejectHandler;
    @Override
    public OrderCreateResponse createOrder(OrderCreateCommand orderCreateCommand) {
        return orderCreateCommandHandler.createOrder(orderCreateCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }

    @Override
    public DriverRejectOrderResponse rejectOrder(TrackOrderQuery trackOrderQuery) {
       return  orderRejectHandler.rejectOrder(trackOrderQuery);
    }
}
