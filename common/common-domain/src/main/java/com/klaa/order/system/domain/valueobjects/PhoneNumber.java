package com.klaa.order.system.domain.valueobjects;

import java.util.regex.Pattern;

public final class PhoneNumber {
    private final String number;


    public PhoneNumber(String number) {
        if (number == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }

        number = number.trim();

        String normalized = normalize(number);

        if (!isValidPhoneNumber(normalized)) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        this.number = normalized;
    }


    private String normalize(String number) {
        if (number.startsWith("+")) {
            return "+" + number.substring(1).replaceAll("\\D", "");
        } else {
            return number.replaceAll("\\D", "");
        }
    }


    private boolean isValidPhoneNumber(String number) {
        if (!Pattern.matches("\\+?\\d+", number)) {
            return false;
        }

        int digitCount = number.startsWith("+") ? number.length() - 1 : number.length();
        return digitCount >= 7 && digitCount <= 15;
    }

    public String getNumber() {
        return number;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return number.equals(that.number);
    }


    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return number;
    }
}