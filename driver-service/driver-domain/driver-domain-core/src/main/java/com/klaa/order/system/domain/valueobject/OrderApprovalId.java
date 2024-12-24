package com.klaa.order.system.domain.valueobject;

import com.klaa.order.system.domain.entity.BaseEntity;
import com.klaa.order.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {

    protected OrderApprovalId(UUID value) {
        super(value);
    }
}
