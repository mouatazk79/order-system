package com.klaa.order.system.driver.service.domain.mapper;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.reject.DriverRejectResponse;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

@Component
public class DriverDataMapper {

    public OrderApproval driverRequestToOrderApproval(DriverRequest driverRequest) {
        return null;
    }

    public DriverRejectResponse orderDriverApprovalEventToDriverRejectResponse(OrderDriverApprovalEvent approvalEvent) {
        return null;
    }

    public OrderEventPayload orderApprovalEventToOrderEventPayload(OrderDriverApprovalEvent approvalEvent) {
        return null;
    }
}
