package com.klaa.order.system.domain.order.service.domain.dto.reject;

import com.klaa.order.system.domain.valueobjects.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class RejectOrderResponse {
    @NotNull
    private final UUID orderTrackingId;
    @NotNull
    private final OrderStatus orderStatus;
}
