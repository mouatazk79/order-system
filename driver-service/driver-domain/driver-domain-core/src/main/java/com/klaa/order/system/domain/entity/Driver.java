package com.klaa.order.system.domain.entity;

import com.klaa.order.system.domain.valueobjects.DriverId;

public class Driver extends AggregateRoot<DriverId> {
private String firstName;
private String lastName;
private String email;
private String car;
private String phoneNumber;
private boolean active;

private Driver(Builder builder){
    super.setId(builder.driverId);
     firstName=builder.firstName;
     lastName=builder.lastName;
     email=builder.email;
     car=builder.car;
     phoneNumber=builder.phoneNumber;
     active=builder.active;

}
    public boolean isActive() {
        return active;
    }

public static Builder builder() {
        return new Builder();
    }


public static final class Builder{

    private DriverId driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String car;
    private String phoneNumber;
    private boolean active;

    public Builder() {
    }
    public Builder restaurantId(DriverId val) {
        driverId = val;
        return this;
    }
    public Builder firstName(String val) {
        firstName = val;
        return this;
    }
    public Builder lastName(String val) {
        lastName = val;
        return this;
    }
    public Builder email(String val) {
        email = val;
        return this;
    }
    public Builder car(String val) {
        car = val;
        return this;
    }
    public Builder phoneNumber(String val) {
        phoneNumber = val;
        return this;
    }
    public Builder active(boolean val) {
        active = val;
        return this;
    }

public Driver build(){
        return new Driver(this);
}
}
}
