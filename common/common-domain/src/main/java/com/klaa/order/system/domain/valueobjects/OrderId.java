package com.klaa.order.system.domain.valueobjects;

import java.util.UUID;

public class OrderId extends BaseId<UUID>{
    public OrderId(UUID value) {
        super(value);
    }
}
