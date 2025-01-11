package com.klaa.order.system.payment.service.data.creditentry.mapper;

import com.klaa.order.service.payment.service.domain.entity.CreditEntry;
import com.klaa.order.service.payment.service.domain.valueobject.CreditEntryId;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.payment.service.data.creditentry.entity.CreditEntryEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.builder()
                .creditEntryId(new CreditEntryId(creditEntryEntity.getId()))
                .userId(new UserId(creditEntryEntity.getUserId()))
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .userId(creditEntry.getUserId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }

}
