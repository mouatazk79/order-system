package com.klaa.order.system.driver.service.domain.entity;

import com.klaa.order.system.domain.entity.AggregateRoot;
import com.klaa.order.system.domain.valueobjects.*;

public class Driver extends AggregateRoot<DriverId> {
private PersonName personName;
private Email email;
private Car car;
private PhoneNumber phoneNumber;
private boolean active;

private Driver(Builder builder){
     super.setId(builder.driverId);
     personName=builder.personName;
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
    private PersonName personName;
    private Email email;
    private Car car;
    private PhoneNumber phoneNumber;
    private boolean active;

    public Builder() {
    }
    public Builder driverId(DriverId val) {
        driverId = val;
        return this;
    }
    public Builder personName(PersonName val) {
        personName = val;
        return this;
    }

    public Builder email(Email val) {
        email = val;
        return this;
    }
    public Builder car(Car val) {
        car = val;
        return this;
    }
    public Builder phoneNumber(PhoneNumber val) {
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
