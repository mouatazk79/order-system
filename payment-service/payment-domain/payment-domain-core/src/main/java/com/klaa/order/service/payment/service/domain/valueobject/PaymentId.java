package com.klaa.order.service.payment.service.domain.valueobject;


import com.klaa.order.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
