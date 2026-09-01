package com.saucedemo.data;

public class CustomerInfo {
    private final String firstName;
    private final String lastName;
    private final String postalCode;

    public CustomerInfo(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public static CustomerInfo sampleCustomer(){
        return new CustomerInfo("Maria","Correa","10001");
    }
}
