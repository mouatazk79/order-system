package com.klaa.order.system.domain.valueobjects;

public final class Email {
    private final String address;

    public Email(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Email address cannot be null");
        }

        address = address.trim();

        if (!isValidEmail(address)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        // Normalize the domain part to lowercase
        int atIndex = address.indexOf('@');
        String localPart = address.substring(0, atIndex);
        String domainPart = address.substring(atIndex + 1).toLowerCase();
        this.address = localPart + "@" + domainPart;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return address.equals(email.address);
    }


    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public String toString() {
        return address;
    }
}