package com.klaa.order.system.driver.service.domain.dto;

import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.domain.valueobjects.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DriverRequest {
    private String id;
    private String sagaId;
    private UUID driverId;
    private String orderId;
    private DriverOrderStatus orderStatus;
    private Position position;
    private Position destination;
    private BigDecimal price;
    private Instant createdAt;

}