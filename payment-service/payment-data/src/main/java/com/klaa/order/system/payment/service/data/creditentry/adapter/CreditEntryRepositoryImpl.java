package com.klaa.order.system.payment.service.data.creditentry.adapter;

import com.klaa.order.service.payment.service.domain.entity.CreditEntry;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.payment.service.data.creditentry.mapper.CreditEntryDataAccessMapper;
import com.klaa.order.system.payment.service.data.creditentry.repository.CreditEntryJpaRepository;
import com.klaa.order.system.payment.service.domain.ports.output.repository.CreditEntryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    public CreditEntryRepositoryImpl(CreditEntryJpaRepository creditEntryJpaRepository,
                                     CreditEntryDataAccessMapper creditEntryDataAccessMapper) {
        this.creditEntryJpaRepository = creditEntryJpaRepository;
        this.creditEntryDataAccessMapper = creditEntryDataAccessMapper;
    }

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper
                .creditEntryEntityToCreditEntry(creditEntryJpaRepository
                        .save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry)));
    }

    @Override
    public Optional<CreditEntry> findByUserId(UserId userId) {
        return creditEntryJpaRepository
                .findByUserId(userId.getValue())
                .map(creditEntryDataAccessMapper::creditEntryEntityToCreditEntry);
    }
}
