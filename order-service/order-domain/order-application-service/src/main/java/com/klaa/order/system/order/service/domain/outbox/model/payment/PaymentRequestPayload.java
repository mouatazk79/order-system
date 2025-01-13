package com.klaa.order.system.order.service.domain.outbox.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
public class PaymentRequestPayload {
    @JsonProperty
    private String orderId;
    @JsonProperty
    private String userId;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private LocalDateTime createdAt;
    @JsonProperty
    private String paymentOrderStatus;
}
