package com.klaa.order.system.domain.valueobjects;

import java.util.UUID;

public class UserId extends BaseId<UUID>{
    protected UserId(UUID value) {
        super(value);
    }
}
