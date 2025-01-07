/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Entities;

import Utilities.DataValidation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer extends User {
    private String address;
    private LocalDate lastOrderDate;
    private double amount;

    // Constructor
    public Customer(String id, String name, String address, LocalDate lastOrderDate, double amount) throws Exception {
        super(id, name);
        setId(id);
        setName(name);
        setAddress(address);
        setLastOrderDate(lastOrderDate);
        setAmount(amount);
    }

    // ----------------------------------------------------------
    @Override
    public void setId(String value) throws Exception {
        if (!DataValidation.checkStringWithFormat(value.toUpperCase(), "E\\d{3}")) {
            throw new Exception("Id invalid. The correct format:Exxx, with x is digits");
        }
        this.id = value;
    }

    // ----------------------------------------------------------
    public String getAddress() {
        return address;
    }

    // ----------------------------------------------------------
    public void setAddress(String address) {
        this.address = address;
    }

    // ----------------------------------------------------------
    public LocalDate getLastOrderDate() {
        return lastOrderDate;
    }

    // ----------------------------------------------------------
    public void setLastOrderDate(LocalDate lastOrderDate) throws Exception {
        if (lastOrderDate == null) {
            throw new Exception("LastOrderDate is invalid");
        }
        this.lastOrderDate = lastOrderDate;
    }

    // ----------------------------------------------------------
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // ----------------------------------------------------------
    public double getAmount() {
        return amount;
    }

    // ----------------------------------------------------------
    @Override
    public String toString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s, %s, %s, %s, %.2f", getId(), getName(), getAddress(), lastOrderDate.format(formatters),
                amount);
    }

}
