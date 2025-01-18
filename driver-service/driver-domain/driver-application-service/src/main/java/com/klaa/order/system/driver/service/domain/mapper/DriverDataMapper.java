package com.klaa.order.system.driver.service.domain.mapper;

import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.entity.OrderDetail;
import com.klaa.order.system.driver.service.domain.event.OrderDriverApprovalEvent;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderEventPayload;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.outbox.OutboxStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component("domainDriverDataMapper")
public class DriverDataMapper {

    public OrderOutboxMessage driverRequestToOrderOutboxMessage(DriverRequest driverRequest){
        return OrderOutboxMessage.builder()
                .id(driverRequest.getOrderId())
                .sagaId(UUID.fromString(driverRequest.getSagaId()))
                .createdAt(LocalDateTime.now())
                .type("OrderProcessingSaga")
                .outboxStatus(OutboxStatus.STARTED)
                .driverOrderStatus(driverRequest.getDriverOrderStatus())
                .build();

    }

    public OrderApproval driverRequestToOrderApproval(DriverRequest driverRequest) {
        return OrderApproval.builder()
                .driverId(new DriverId(driverRequest.getDriverId()))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(driverRequest.getOrderId()))
                        .position(driverRequest.getPosition())
                        .destination(driverRequest.getDestination())
                        .price(new Money(driverRequest.getPrice()))
                        .build()
                )
                .driverOrderStatus(driverRequest.getDriverOrderStatus())
                .build();
    }

    public DriverResponse orderDriverApprovalEventToDriverRejectResponse(OrderDriverApprovalEvent approvalEvent) {
        return DriverResponse.builder()
                .orderId(approvalEvent.getOrderApproval().getId().toString())
                .driverOrderStatus(approvalEvent.getOrderApproval().getOrderStatus()).build();
    }

    public OrderEventPayload orderApprovalEventToOrderEventPayload(OrderDriverApprovalEvent approvalEvent) {
        return OrderEventPayload.builder()
                .orderId(approvalEvent.getOrderApproval().getId().toString())
                .driverId(approvalEvent.getOrderApproval().getDriverId().toString())
                .createdAt(approvalEvent.getLocalDateTime())
                .orderApprovalStatus(approvalEvent.getOrderApproval().toString())
                .failureMessages(approvalEvent.getFailureMessages())
                .build();
    }
}
