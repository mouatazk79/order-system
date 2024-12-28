package com.klaa.order.system.payment.service.domain.ports.output.repository;


import com.klaa.order.service.payment.service.domain.entity.CreditEntry;
import com.klaa.order.system.domain.valueobjects.UserId;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByUserId(UserId userId);
}
