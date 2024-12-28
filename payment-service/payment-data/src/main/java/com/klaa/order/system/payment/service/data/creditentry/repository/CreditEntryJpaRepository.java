package com.klaa.order.system.payment.service.data.creditentry.repository;


import com.klaa.order.system.payment.service.data.creditentry.entity.CreditEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditEntryJpaRepository extends JpaRepository<CreditEntryEntity, UUID> {

    Optional<CreditEntryEntity> findByUserId(UUID userId);


}
