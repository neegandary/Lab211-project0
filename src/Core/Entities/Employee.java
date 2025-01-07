/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Entities;

import Utilities.DataValidation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee extends User {
    private LocalDate dateOfBirth;
    private double salary;
    private String email;

    // Constructor
    public Employee(String id, String name, LocalDate dateOfBirth, String email, double salary) throws Exception {
        super(id, name);
        setId(id);
        setEmail(email);
        setName(name);
        setDateOfBirth(dateOfBirth);
        setSalary(salary);
    }

    @Override
    public void setId(String value) throws Exception {
        if (!DataValidation.checkStringWithFormat(value.toUpperCase(), "E\\d{3}")) {
            throw new Exception("Id is invalid. The correct format: Exxx, with x is digits");
        }
        this.id = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (!DataValidation.checkStringWithFormat(email, "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new Exception("Email is invalid");
        }
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) throws Exception {
        if (dateOfBirth == null) {
            throw new Exception("DateOfBirth is invalid");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Methods...
    @Override
    public String toString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s, %s, %s, %.2f", getId(), getName(), dateOfBirth.format(formatters), email, salary);
    }
}