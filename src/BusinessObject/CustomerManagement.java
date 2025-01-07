/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import Core.Entities.Customer;
import Core.Interfaces.ICustomerDAO;
import Presentation.Menu;
import Utilities.DataInput;

public class CustomerManagement {
    ICustomerDAO customerDAO;

    public CustomerManagement(ICustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void processMenuForCustomer() {
        boolean stop = true;
        try {
            do {
                Menu.print("******Customer Management******|1.Add Customer"
                        + "|2.Update Customer |3.Remove Customer"
                        + "|4.Search Customers |5.Print Customer List "
                        + "|6.Export to file |7.Back to main menu |Select:");
                int choice = Menu.getUserChoice();
                switch (choice) {
                    case 1 -> {
                        addNewCustomer();
                    }
                    case 2 -> {
                        updateCustomer();
                    }
                    case 3 -> {
                        deleteCustomer();
                    }
                    case 4 -> {
                        searchCustomer();
                    }
                    case 5 -> {
                        System.out.println(">>Customer List:");
                        printList(customerDAO.getCustomers());
                    }
                    case 6 -> {
                        exportToFile();
                        System.out.println(">>The customer list has successfully exported.");
                    }
                    case 7 -> stop = false;
                    default -> System.out.println(">>Choice invalid");
                }
            } while (stop);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Customer inputCustomer() throws Exception {
        String id = DataInput.getString("Enter the id:");
        String name = DataInput.getString("Enter name:");
        LocalDate lastOrderDate = DataInput.getDate("Enter last order date:");
        String address = DataInput.getString("Enter address:");
        double amount = DataInput.getDoubleNumber("Enter amount:");
        return new Customer(id, name, address, lastOrderDate, amount);
    }

    public void setNewCustomerInfo(Customer customer) throws Exception {
        String name = DataInput.getString("Enter new name:");
        if (!name.isEmpty()) {
            customer.setName(name);
        }
        LocalDate lastOrderDate = DataInput.getDate("Enter last order date:");
        if (lastOrderDate != null) {
            customer.setLastOrderDate(lastOrderDate);
        }
        String address = DataInput.getString("Enter address:");
        if (!address.isEmpty()) {
            customer.setAddress(address);
        }
        double amount = DataInput.getDoubleNumber("Enter amount:");
        if (amount > 0) {
            customer.setAddress(address);
        }
    }

    public void addNewCustomer() {
        try {
            Customer customer = inputCustomer();
            if (customerDAO.getCustomerById(customer.getId()) != null) {
                System.out.println(">>The customer already exist");
                return;
            }
            customerDAO.addCustomer(customer);
            System.out.println(">>The customer has added successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCustomer() {
        try {
            String id = DataInput.getString("Enter customer id:");
            Customer customer = customerDAO.getCustomerById(id);
            if (customer == null) {
                System.err.println("Customer do not exist");
                return;
            }
            setNewCustomerInfo(customer);
            customerDAO.updateCustomer(customer);
            System.out.println("Customer update sucessfully");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteCustomer() {
        try {
            String id = DataInput.getString("Enter customer id:");
            Customer customer = customerDAO.getCustomerById(id);
            if (customer == null) {
                System.err.println("Customer do not exist");
                return;
            }
            customerDAO.removeCustomer(customer);
            System.err.println("Delete customer sucessfully");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void findCustomerById() {
        Customer customer;
        try {
            String id = DataInput.getString("Enter customer id:");
            customer = customerDAO.getCustomerById(id);
            if (customer != null) {
                System.err.println(customer);
            } else {
                System.out.format("The customer id: %d not found", id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void searchCustomer() throws Exception {
        int choice;
        boolean stop = true;
        String value;
        try {
            do {
                Menu.print("1.Search by address|2.Search by amount|3.Back |Select:");
                choice = DataInput.getIntegerNumber();
                switch (choice) {
                    case 1 -> {
                        value = DataInput.getString("Enter name:");
                        List<Customer> customers = searchCustomerByName(value);
                        if (!customers.isEmpty()) {
                            printList(customers);
                        } else {
                            System.out.format("The customers with name :%s not found.%n", value);
                        }
                    }
                    case 2 -> {
                        double amount = DataInput.getDoubleNumber("Enter amount:");
                        List<Customer> customers = searchCustomerBySalary(amount);
                        if (!customers.isEmpty()) {
                            printList(customers);
                        } else {
                            System.out.format("The customers with amount are greater than of equal to :%s not found.%n",
                                    String.valueOf(amount));
                        }
                    }
                    case 3 -> {
                        stop = false;
                    }
                    default -> {
                        System.out.println("Choice invalid");
                    }
                }
            } while (stop);
        } catch (Exception e) {
        }
    }

    public void printList(List<Customer> customers) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.format("%-10s | %20s | %-20s | %-19s | %s%n", "Customer Id", "Customer Name", "Last Order Date",
                "Address", "Amount");
        System.out.println(String.join("", Collections.nCopies(95, "-")));
        for (Customer employee : customers) {
            System.out.format("%-11s | %-20s | %-20s | %-20s | %.2f%n", employee.getId(), employee.getName(),
                    employee.getLastOrderDate().format(formatter), employee.getAddress(), employee.getAmount());
        }
        System.out.println(String.join("", Collections.nCopies(95, "-")));
    }

    public void exportToFile() throws Exception {
        customerDAO.saveCustomerListToFile();
    }

    public List<Customer> searchCustomerByName(String name) throws Exception {
        Predicate<Customer> predicate = e -> e.getName().toLowerCase().contains(name.toLowerCase());
        List<Customer> customers = customerDAO.search(predicate);
        customers.sort((Customer e1, Customer e2) -> {
            if (e1.getName().compareToIgnoreCase(e2.getName()) > 0) {
                return 1;
            } else if (e1.getName().compareToIgnoreCase(e2.getName()) == 0) {
                return Double.compare(e2.getAmount(), e1.getAmount());
            } else
                return -1;
        });
        return customers;
    }

    public List<Customer> searchCustomerBySalary(double amount) throws Exception {
        Predicate<Customer> predicate = e -> e.getAmount() >= amount;
        List<Customer> customers = customerDAO.search(predicate);
        customers.sort((Customer e1, Customer e2) -> {
            if (Double.compare(e1.getAmount(), e2.getAmount()) == 1) {
                return 1;
            } else if (Double.compare(e1.getAmount(), e2.getAmount()) == 0) {
                return e1.getName().compareToIgnoreCase(e2.getName());
            } else
                return -1;
        });
        return customers;
    }
}