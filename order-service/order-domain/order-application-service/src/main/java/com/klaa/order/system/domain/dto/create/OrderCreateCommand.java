package com.klaa.order.system.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class OrderCreateCommand {
    @NotNull
    private final UUID customerId;
    @NotNull
    private final UUID driverId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final PositionAddress address;
}
