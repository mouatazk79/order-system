package com.klaa.order.system.domain.ports.input.service;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.dto.remove.RejectOrderResponse;
import com.klaa.order.system.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    OrderCreateResponse createOrder(@Valid OrderCreateCommand orderCreateCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
    RejectOrderResponse rejectOrder(@Valid TrackOrderQuery trackOrderQuery);
}
