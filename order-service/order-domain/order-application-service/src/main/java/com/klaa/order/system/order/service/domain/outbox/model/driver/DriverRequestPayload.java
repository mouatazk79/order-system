package com.klaa.order.system.order.service.domain.outbox.model.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klaa.order.system.order.service.domain.dto.create.PositionAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class DriverRequestPayload {
    @JsonProperty
    private String orderId;
    @JsonProperty
    private String driverId;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private PositionAddress position;
    @JsonProperty
    private PositionAddress destination;
    @JsonProperty
    private LocalDateTime createdAt;
    @JsonProperty
    private String orderStatus;
}
