package com.klaa.order.system.domain.order.service.domain.dto.message;

import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DriverResponse {
    private String id;
    private String sagaId;
    private UUID orderId;
    private String driverId;
    private Instant createdAt;
    private DriverOrderStatus driverOrderStatus;
    private List<String> failureMessages;

}
