package com.klaa.order.system.order.service.data.user.adapter;

import com.klaa.order.system.order.service.data.user.mapper.UserEntityMapper;
import com.klaa.order.system.order.service.data.user.repository.UserJpaRepository;
import com.klaa.order.system.domain.order.service.domain.entity.User;
import com.klaa.order.system.order.service.domain.ports.output.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    @Override
    public Optional<User> findDriverById(UUID id) {
        return userJpaRepository.findById(id).map(userEntityMapper::userEntityToUser);
    }
}
