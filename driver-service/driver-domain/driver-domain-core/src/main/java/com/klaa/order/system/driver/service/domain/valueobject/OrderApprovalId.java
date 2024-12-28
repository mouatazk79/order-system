package com.klaa.order.system.driver.service.domain.valueobject;

import com.klaa.order.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {

    protected OrderApprovalId(UUID value) {
        super(value);
    }
}
