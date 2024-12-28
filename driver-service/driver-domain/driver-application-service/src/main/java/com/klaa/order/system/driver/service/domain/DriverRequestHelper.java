package com.klaa.order.system.driver.service.domain;

import com.klaa.order.system.driver.service.domain.dto.DriverRequest;
import com.klaa.order.system.driver.service.domain.entity.Driver;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.exception.DriverNotFoundException;
import com.klaa.order.system.driver.service.domain.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverApprovedRequestMessagePublisher;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverFailedMessagePublisher;
import com.klaa.order.system.driver.service.domain.ports.output.publisher.DriverRejectedRequestMessagePublisher;
import com.klaa.order.system.driver.service.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    private final DriverApprovedRequestMessagePublisher driverApprovedRequestMessagePublisher;
    private final DriverRejectedRequestMessagePublisher driverRejectedRequestMessagePublisher;
    private final DriverFailedMessagePublisher driverFailedMessagePublisher;


    public OrderDriverApprovalEvent persistDriverRequest(DriverRequest driverRequest) {
        log.info("received driver request with id: {}",driverRequest.getOrderId());
        checkDriver(driverRequest.getDriverId());
        OrderApproval orderApproval=driverDataMapper.driverRequestToOrderApproval(driverRequest);
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent=driverDomainService
                .validateAndApproveOrder(orderApproval,failureMessages,driverApprovedRequestMessagePublisher,driverFailedMessagePublisher);
        persistOrderApproval(orderApproval);
        return approvalEvent;
        }

    public OrderDriverApprovalEvent persistCancelPayment(DriverRequest driverRequest) {
        log.info("Received driver reject for order id: {}", driverRequest.getOrderId());
        OrderApproval orderApproval=driverDataMapper.driverRequestToOrderApproval(driverRequest);
        List<String> failureMessages = new ArrayList<>();
        OrderDriverApprovalEvent approvalEvent=driverDomainService
                .validateAndRejectOrder(orderApproval,failureMessages,driverApprovedRequestMessagePublisher,driverRejectedRequestMessagePublisher);
        persistOrderApproval(orderApproval);
        return approvalEvent;
    }

    private void persistOrderApproval(OrderApproval orderApproval) {
        Optional<OrderApproval> newOrderApproval=approvalRepository.orderApproval(orderApproval);
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


}
