package com.klaa.order.system.domain.valueobjects;

import java.util.UUID;

public class TrackingId extends BaseId<UUID>{
    public TrackingId(UUID value) {
        super(value);
    }
}
