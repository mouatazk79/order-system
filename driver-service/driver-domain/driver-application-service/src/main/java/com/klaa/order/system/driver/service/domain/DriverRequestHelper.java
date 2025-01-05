package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.entity.Driver;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.exception.DriverNotFoundException;
import com.klaa.order.system.driver.service.domain.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.driver.service.domain.outbox.scheduler.OrderOutboxHelper;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverApprovalResponseMessagePublisher;
import com.klaa.order.system.driver.service.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class DriverRequestHelper {

    private final DriverRepository driverRepository;
    private final OrderApprovalRepository approvalRepository;
    private final DriverDataMapper driverDataMapper;
    private final DriverDomainService driverDomainService;
    private final OrderOutboxHelper orderOutboxHelper;
    private final DriverApprovalResponseMessagePublisher driverApprovalResponseMessagePublisher;



    @Transactional
    public void persistDriverRequest(DriverRequest driverRequest) {
        log.info("received driver request with id: {}",driverRequest.getOrderId());
        checkDriver(driverRequest.getDriverId());
        OrderApproval orderApproval=driverDataMapper.driverRequestToOrderApproval(driverRequest);
        List<String> failureMessages = new ArrayList<>();


        OrderDriverApprovalEvent approvalEvent=driverDomainService
                .validateAndApproveOrder(orderApproval,failureMessages);

        persistOrderApproval(orderApproval);

        orderOutboxHelper
                .saveOrderOutboxMessage(driverDataMapper.orderApprovalEventToOrderEventPayload(approvalEvent),
                        approvalEvent.getOrderApproval().getOrderStatus(),
                        OutboxStatus.STARTED,
                        UUID.fromString(driverRequest.getSagaId()));


        }

    @Transactional
    public void persistCancelPayment(DriverRequest driverRequest) {
        log.info("Received driver reject for order id: {}", driverRequest.getOrderId());
        OrderApproval orderApproval=driverDataMapper.driverRequestToOrderApproval(driverRequest);
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent=driverDomainService
                .validateAndRejectOrder(orderApproval,failureMessages);
        persistOrderApproval(orderApproval);
        orderOutboxHelper
                .saveOrderOutboxMessage(driverDataMapper.orderApprovalEventToOrderEventPayload(approvalEvent),
                        approvalEvent.getOrderApproval().getOrderStatus(),
                        OutboxStatus.STARTED,
                        UUID.fromString(driverRequest.getSagaId()));

    }

    private void persistOrderApproval(OrderApproval orderApproval) {
        Optional<OrderApproval> newOrderApproval=approvalRepository.saveOrderApproval(orderApproval);
        if (newOrderApproval.isEmpty()){
            throw new DriverDomainException("");
        }

    }


    private void checkDriver(UUID id){
        Optional<Driver> driver=driverRepository.findDriverById(id);
        if(driver.isEmpty()){
            throw new DriverNotFoundException("driver with id: "+id+"does not exist");
        }
    }

    private boolean publishIfOutboxMessageProcessed(DriverRequest driverRequest) {
        Optional<OrderOutboxMessage> orderOutboxMessage =
                orderOutboxHelper.getCompletedOrderOutboxMessageBySagaIdAndOutboxStatus(UUID
                        .fromString(driverRequest.getSagaId()), OutboxStatus.COMPLETED);
        if (orderOutboxMessage.isPresent()) {
            driverApprovalResponseMessagePublisher.publish(orderOutboxMessage.get(),
                    orderOutboxHelper::updateOutboxStatus);
            return true;
        }
        return false;
    }


}
