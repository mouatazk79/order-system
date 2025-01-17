package com.klaa.order.system.domain.order.service.domain.entity;

import com.klaa.order.system.domain.entity.AggregateRoot;
import com.klaa.order.system.domain.valueobjects.*;



public class User extends AggregateRoot<UserId> {


    private User(User.Builder builder) {
        super.setId(builder.userId);
    }

    public static User.Builder builder() {
        return new User.Builder();
    }

    public static final class Builder {
        private UserId userId;
        private Builder() {
        }


        public User.Builder userId(UserId val) {
            userId = val;
            return this;
        }



        public User build() {
            return new User(this);
        }
    }
}
