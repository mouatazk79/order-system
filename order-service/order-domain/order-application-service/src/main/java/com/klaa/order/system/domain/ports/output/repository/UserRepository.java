package com.klaa.order.system.domain.ports.output.repository;

import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findDriverById(UUID id);

}
