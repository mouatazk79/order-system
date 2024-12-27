package com.klaa.order.system.data.user.adapter;

import com.klaa.order.system.data.user.repository.UserJpaRepository;
import com.klaa.order.system.domain.entity.User;
import com.klaa.order.system.domain.ports.output.repository.UserRepository;
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
