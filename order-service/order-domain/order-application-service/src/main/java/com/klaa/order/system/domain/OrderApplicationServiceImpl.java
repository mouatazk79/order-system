package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {
    @Override
    public OrderCreateResponse createOrder(OrderCreateCommand orderCreateCommand) {
        return null;
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
