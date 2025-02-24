package com.klaa.order.system.domain.order.service.domain.entity;

import com.klaa.order.system.domain.entity.AggregateRoot;
import com.klaa.order.system.domain.valueobjects.Car;
import com.klaa.order.system.domain.valueobjects.DriverId;


public class Driver extends AggregateRoot<DriverId> {
private final Car car;
private boolean active;

    private Driver(Builder builder) {
        super.setId(builder.driverId);
        car = builder.car;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Car getCar() {
        return car;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private DriverId driverId;
        private Car car;
        private boolean active;

        private Builder() {
        }

        public Builder driverId(DriverId val) {
            driverId = val;
            return this;
        }

        public Builder car(Car val) {
            car = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
}
