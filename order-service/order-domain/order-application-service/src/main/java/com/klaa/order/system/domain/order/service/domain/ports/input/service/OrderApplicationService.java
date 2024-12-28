package com.klaa.order.system.domain.order.service.domain.ports.input.service;

import com.klaa.order.system.domain.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.order.service.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.order.service.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.domain.order.service.domain.dto.reject.DriverRejectOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    OrderCreateResponse createOrder(@Valid OrderCreateCommand orderCreateCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
    DriverRejectOrderResponse rejectOrder(@Valid TrackOrderQuery trackOrderQuery);
}
