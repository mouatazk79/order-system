package com.klaa.order.service.payment.service.domain.valueobject;


import com.klaa.order.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
