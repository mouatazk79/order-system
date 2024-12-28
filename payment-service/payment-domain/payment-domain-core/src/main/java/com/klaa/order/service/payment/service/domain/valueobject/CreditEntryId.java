package com.klaa.order.service.payment.service.domain.valueobject;


import com.klaa.order.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
