package com.klaa.order.system.payment.service.domain.ports.output.repository;


import com.klaa.order.service.payment.service.domain.entity.CreditHistory;
import com.klaa.order.system.domain.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByUserId(UserId userId);
}
