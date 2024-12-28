package com.klaa.order.system.order.service.data.user.adapter;

import com.klaa.order.system.order.service.data.user.repository.UserJpaRepository;
import com.klaa.order.system.domain.order.service.domain.entity.User;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    @Override
    public Optional<User> findDriverById(UUID id) {
        return Optional.empty();
    }
}
