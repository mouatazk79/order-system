package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.reject.DriverRejectResponse;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.domain.ports.input.service.DriverApplicationService;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
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



    @Override
    public DriverRejectResponse rejectOrder(DriverRequest driverRequest) {
        OrderApproval orderApproval= checkOrder(driverRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent =driverDomainService.validateAndRejectOrder(orderApproval,failureMessages);
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
