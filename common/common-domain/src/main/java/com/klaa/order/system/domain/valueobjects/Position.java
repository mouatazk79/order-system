package com.klaa.order.system.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class Position {
private final UUID positionId;
private final String streetAddress;
private final String zipCode;
private final String city;

    public Position(UUID positionId, String streetAddress, String zipCode, String city) {
        this.positionId = positionId;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
    }

    public UUID getPositionId() {
        return positionId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(streetAddress, position.streetAddress) && Objects.equals(zipCode, position.zipCode) && Objects.equals(city, position.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, zipCode, city);
    }
}
