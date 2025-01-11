package com.klaa.order.system.order.service.domain.ports.output.repository;

import com.klaa.order.system.domain.order.service.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findDriverById(UUID id);

}
