package com.saucedemo.data;
/**
 * Simple data holder for the checkout "Your Information" form.
 * Keeping this as its own class (instead of passing three loose strings
 * around) makes test and page-object method signatures easier to read.
 */
public class CustomerInfo {
    private final String firstName;
    private final String lastName;
    private final String postalCode;
    /**
     * @param firstName  the customer's first name
     * @param lastName   the customer's last name
     * @param postalCode the customer's postal/zip code
     */
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
    /**
     * Provides a ready-to-use sample customer so tests don't need to
     * repeat literal values every time they build the checkout flow.
     *
     * @return a CustomerInfo populated with sample data
     */
    public static CustomerInfo sampleCustomer(){

        return new CustomerInfo("Maria","Correa","10001");
    }
}
