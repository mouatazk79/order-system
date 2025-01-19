package com.klaa.order.system.driver.service.domain.outbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klaa.order.system.domain.valueobjects.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderEventPayload {
    @JsonProperty
    private UUID orderId;
    @JsonProperty
    private UUID driverId;
    @JsonProperty
    private Position position;
    @JsonProperty
    private Position destination;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private List<String> failureMessages;
    @JsonProperty
    private LocalDateTime createdAt;


}
