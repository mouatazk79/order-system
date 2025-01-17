package com.klaa.order.system.order.service.data.user.repository;

import com.klaa.order.system.order.service.data.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
   Optional<UserEntity> findByUserId(UUID id);
}
