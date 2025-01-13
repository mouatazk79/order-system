package com.klaa.order.system.driver.service.domain.dto.response;

import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DriverResponse {
    private final String orderId;
    private final DriverOrderStatus driverOrderStatus;
}
