package com.shenlx.xinwen.xml;


import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/2/28 10:38
 */

public class Customer {
    private long customerNumber;
    /**
     * Personal name
     */
    private String firstName;

    /**
     * Family name .
     */
    private String lastName;

    /**
     * Middle name(s) if any.
     */
    private List<String> middleNames;


    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerNumber=" + customerNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleNames=" + middleNames +
                '}';
    }
}
