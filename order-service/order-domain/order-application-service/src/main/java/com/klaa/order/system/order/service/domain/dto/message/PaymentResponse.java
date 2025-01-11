package com.klaa.order.system.order.service.domain.dto.message;

import com.klaa.order.system.domain.valueobjects.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String sagaId;
    private UUID orderId;
    private String paymentId;
    private String userId;
    private BigDecimal price;
    private Instant createdAt;
    private PaymentOrderStatus paymentStatus;
    private List<String> failureMessages;
}