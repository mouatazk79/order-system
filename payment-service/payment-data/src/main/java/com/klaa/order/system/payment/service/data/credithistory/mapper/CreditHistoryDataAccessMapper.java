package com.klaa.order.system.payment.service.data.credithistory.mapper;

import com.klaa.order.service.payment.service.domain.entity.CreditHistory;
import com.klaa.order.service.payment.service.domain.valueobject.CreditHistoryId;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.payment.service.data.credithistory.entity.CreditHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditHistoryDataAccessMapper {

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity) {
        return CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                .userId(new UserId(creditHistoryEntity.getUserId()))
                .amount(new Money(creditHistoryEntity.getAmount()))
                .transactionType(creditHistoryEntity.getType())
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .userId(creditHistory.getUserId().getValue())
                .amount(creditHistory.getAmount().getAmount())
                .type(creditHistory.getTransactionType())
                .build();
    }

}
