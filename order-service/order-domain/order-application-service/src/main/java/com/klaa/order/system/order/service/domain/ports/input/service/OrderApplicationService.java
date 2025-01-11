package com.klaa.order.system.order.service.domain.ports.input.service;

import com.klaa.order.system.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.order.service.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.order.service.domain.dto.reject.RejectOrderResponse;
import com.klaa.order.system.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.order.service.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    OrderCreateResponse createOrder(@Valid OrderCreateCommand orderCreateCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
    RejectOrderResponse rejectOrder(@Valid TrackOrderQuery trackOrderQuery);
}
