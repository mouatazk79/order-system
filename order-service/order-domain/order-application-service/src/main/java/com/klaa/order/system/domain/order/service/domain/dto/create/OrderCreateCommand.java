package com.klaa.order.system.domain.order.service.domain.dto.create;

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
    private final UUID userId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final PositionAddress position;
    @NotNull
    private final PositionAddress destinationAddress;
}
