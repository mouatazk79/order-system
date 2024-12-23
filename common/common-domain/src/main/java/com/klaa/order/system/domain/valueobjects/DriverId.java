package com.klaa.order.system.domain.valueobjects;

import java.util.UUID;

public class DriverId extends BaseId<UUID>{
    protected DriverId(UUID value) {
        super(value);
    }
}
