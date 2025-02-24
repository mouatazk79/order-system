package com.klaa.order.system.domain.valueobjects;

import java.util.Objects;

public final class Car {
    private final String make;
    private final String model;
    private final int year;
    private final String vin;


    public Car(String make, String model, int year, String vin) {
        if (make == null || make.trim().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be null or empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (vin == null || vin.trim().isEmpty()) {
            throw new IllegalArgumentException("VIN cannot be null or empty");
        }
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }

        this.make = make.trim().toLowerCase();
        this.model = model.trim().toLowerCase();
        this.vin = vin.trim().toUpperCase();
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getVin() {
        return vin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year &&
                make.equals(car.make) &&
                model.equals(car.model) &&
                vin.equals(car.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, year, vin);
    }


    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", vin='" + vin + '\'' +
                '}';
    }
}