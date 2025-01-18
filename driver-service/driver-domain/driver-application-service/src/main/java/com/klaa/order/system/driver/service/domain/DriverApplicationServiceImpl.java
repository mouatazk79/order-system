package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.dto.approval.ApprovalCommand;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.driver.service.domain.outbox.scheduler.OrderOutboxHelper;
import com.klaa.order.system.driver.service.domain.ports.input.service.DriverApplicationService;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public DriverResponse approveOrder(ApprovalCommand approvalCommand) {
        OrderApproval orderApproval= checkOrderApproval(approvalCommand.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent=driverDomainService.validateAndApproveOrder(orderApproval,failureMessages);
        OrderOutboxMessage orderOutboxMessage=checkOrderOutboxMessage(approvalCommand.getSagaId());
        orderOutboxMessage.setDriverOrderStatus(approvalEvent.getOrderApproval().getDriverOrderStatus());
        orderOutboxHelper.saveOrderOutboxMessage(orderOutboxMessage);
        approvalRepository.saveOrderApproval(approvalEvent.getOrderApproval());
        return driverDataMapper.orderDriverApprovalEventToDriverRejectResponse(approvalEvent);
    }
    @Override
    @Transactional
    public DriverResponse rejectOrder(ApprovalCommand approvalCommand) {
        OrderApproval orderApproval= checkOrderApproval(approvalCommand.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent =driverDomainService.validateAndRejectOrder(orderApproval,failureMessages);
        OrderOutboxMessage orderOutboxMessage=checkOrderOutboxMessage(approvalCommand.getSagaId());
        orderOutboxMessage.setDriverOrderStatus(approvalEvent.getOrderApproval().getDriverOrderStatus());
        orderOutboxHelper.saveOrderOutboxMessage(orderOutboxMessage);
        approvalRepository.saveOrderApproval(approvalEvent.getOrderApproval());
        return driverDataMapper.orderDriverApprovalEventToDriverRejectResponse(approvalEvent);
    }

    private OrderApproval checkOrderApproval(UUID orderApprovalId){
        Optional<OrderApproval> orderApproval=approvalRepository.findByOrderId(orderApprovalId);
        if(orderApproval.isEmpty()){
            throw new DriverDomainException("orderApproval does not exist");
        }
        return orderApproval.get();

    }
    private OrderOutboxMessage checkOrderOutboxMessage(UUID sagaId){
        Optional<OrderOutboxMessage> orderOutboxMessage=orderOutboxHelper.getOrderOutboxMessageBySagaIdAndOutboxStatus(sagaId,OutboxStatus.STARTED);
        if (orderOutboxMessage.isEmpty()){
            throw new DriverDomainException("orderOutboxMessage does not exist");
        }
        return orderOutboxMessage.get();
    }


}
