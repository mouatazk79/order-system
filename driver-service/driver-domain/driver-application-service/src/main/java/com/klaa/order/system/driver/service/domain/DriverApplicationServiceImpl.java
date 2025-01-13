package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.domain.outbox.scheduler.OrderOutboxHelper;
import com.klaa.order.system.driver.service.domain.ports.input.service.DriverApplicationService;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DriverApplicationServiceImpl implements DriverApplicationService {
    private final OrderApprovalRepository approvalRepository;
    private final DriverDomainService driverDomainService;
    private final DriverDataMapper driverDataMapper;
    private final OrderOutboxHelper orderOutboxHelper;


    @Override
    public DriverResponse approveOrder(DriverRequest driverRequest) {
        OrderApproval orderApproval= checkOrder(driverRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent=driverDomainService.validateAndApproveOrder(orderApproval,failureMessages);
        orderOutboxHelper
                .saveOrderOutboxMessage(driverDataMapper.orderApprovalEventToOrderEventPayload(approvalEvent),
                        approvalEvent.getOrderApproval().getOrderStatus(),
                        OutboxStatus.STARTED,
                        UUID.fromString(driverRequest.getSagaId()));
        return driverDataMapper.orderDriverApprovalEventToDriverRejectResponse(approvalEvent);
    }

    @Override
    public DriverResponse rejectOrder(DriverRequest driverRequest) {
        OrderApproval orderApproval= checkOrder(driverRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent =driverDomainService.validateAndRejectOrder(orderApproval,failureMessages);
        orderOutboxHelper
                .saveOrderOutboxMessage(driverDataMapper.orderApprovalEventToOrderEventPayload(approvalEvent),
                        approvalEvent.getOrderApproval().getOrderStatus(),
                        OutboxStatus.STARTED,
                        UUID.fromString(driverRequest.getSagaId()));

        return driverDataMapper.orderDriverApprovalEventToDriverRejectResponse(approvalEvent);
    }

    public OrderApproval checkOrder(UUID orderId){
        Optional<OrderApproval> orderApproval=approvalRepository.findOrderId(orderId);
        if(orderApproval.isEmpty()){
            throw new DriverDomainException("order does not exist");
        }
        return orderApproval.get();

    }
}
