package com.klaa.order.system.order.service.data.user.mapper;

import com.klaa.order.system.domain.order.service.domain.entity.User;
import com.klaa.order.system.domain.valueobjects.UserId;
import com.klaa.order.system.order.service.data.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
    public User userEntityToUser(UserEntity userEntity) {
        return User.builder()
                .userId(new UserId(userEntity.getUserId()))
                .build();
    }
}
