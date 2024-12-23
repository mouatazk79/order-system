package com.klaa.order.system.domain.entity;

import com.klaa.order.system.domain.valueobjects.DriverId;


public class Driver extends AggregateRoot<DriverId>{
private final String car;
private boolean active;

    private Driver(Builder builder) {
        super.setId(builder.driverId);
        car = builder.car;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCar() {
        return car;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private DriverId driverId;
        private String car;
        private boolean active;

        private Builder() {
        }

        public Builder driverId(DriverId val) {
            driverId = val;
            return this;
        }

        public Builder car(String val) {
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
