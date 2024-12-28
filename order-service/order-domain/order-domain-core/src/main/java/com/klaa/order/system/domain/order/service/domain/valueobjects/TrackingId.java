package com.klaa.order.system.domain.order.service.domain.valueobjects;

import com.klaa.order.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
