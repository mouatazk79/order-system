package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.DriverRequest;
import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.OrderApproval;
import com.klaa.order.system.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.domain.event.OrderDriverRejectedEvent;
import com.klaa.order.system.domain.exception.DriverNotFoundException;
import com.klaa.order.system.domain.mapper.DriverDataMapper;
import com.klaa.order.system.domain.ports.input.listener.DriverRequestListener;
import com.klaa.order.system.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
@AllArgsConstructor
public class DriverRequestListenerImpl implements DriverRequestListener {
    private final DriverRequestHelper driverRequestHelper;


    @Override
    public void orderRequest(DriverRequest driverRequest) {
        OrderDriverApprovalEvent approvalEvent= driverRequestHelper.persistDriverRequest(driverRequest);
        fireEvent(approvalEvent);
    }

    @Override
    public void orderCancelled(DriverRequest driverRequest) {
        OrderDriverApprovalEvent approvalEvent=driverRequestHelper.persistCancelPayment(driverRequest);
        fireEvent(approvalEvent);
    }


    private void fireEvent(OrderDriverApprovalEvent approvalEvent) {
        approvalEvent.fire();
    }

}
