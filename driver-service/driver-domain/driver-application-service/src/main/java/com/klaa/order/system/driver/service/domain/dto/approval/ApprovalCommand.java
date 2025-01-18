package com.klaa.order.system.driver.service.domain.dto.approval;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ApprovalCommand {
    @NotNull
    private final UUID orderId;
    @NotNull
    private UUID sagaId;

}
