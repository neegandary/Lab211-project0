/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import Core.Interfaces.ICustomerDAO;
import Core.Entities.Customer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CustomerDAO implements ICustomerDAO {
    private List<Customer> customerList = new ArrayList<>();
    private final FileManager fileManager;

    public CustomerDAO(String fileManager) throws Exception {
        this.fileManager = new FileManager(fileManager);
        loadDataFromFile();
    }

    // ----------------------------------------------------------
    public void loadDataFromFile() throws Exception {
        String id, name, address;
        LocalDate lastOrderDate;
        double amount;
        try {
            customerList.clear();
            List<String> cusData = fileManager.readDataFromFile();
            for (String e : cusData) {
                List<String> cus = Arrays.asList(e.split(","));
                id = cus.get(0).trim();
                name = cus.get(1).trim();
                address = cus.get(2).trim();
                lastOrderDate = LocalDate.parse(cus.get(3).trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                amount = Double.parseDouble(cus.get(4).trim());
                Customer newCus = new Customer(id, name, address, lastOrderDate, amount);
                customerList.add(newCus);
                if (customerList.isEmpty()) {
                    throw new Exception("Customer list is empty.");
                }
            }
        } catch (Exception ex) {
            throw new Exception("Can not read data from file.Please check file again.");
        }
    }

    // ----------------------------------------------------------
    @Override
    public List<Customer> getCustomers() throws Exception {
        Collections.sort(customerList, (c1, c2) -> c1.getId().compareTo(c2.getId()));
        return customerList;
    }

    // ----------------------------------------------------------
    @Override
    public Customer getCustomerById(String id) throws Exception {
        if (customerList.isEmpty())
            getCustomers();
        return customerList.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    // ----------------------------------------------------------
    @Override
    public void addCustomer(Customer customer) throws Exception {
        customerList.add(customer);
    }

    // ----------------------------------------------------------
    @Override
    public void updateCustomer(Customer customer) throws Exception {
        Customer cus = getCustomerById(customer.getId());
        if (cus != null) {
            cus.setAddress(customer.getAddress());
            cus.setLastOrderDate(customer.getLastOrderDate());
            cus.setAmount(cus.getAmount());
            cus.setName(customer.getName());
        }
    }

    // ----------------------------------------------------------
    @Override
    public void removeCustomer(Customer customer) throws Exception {
        Customer cus = getCustomerById(customer.getId());
        if (cus != null)
            customerList.remove(cus);
    }

    // ----------------------------------------------------------
    @Override
    public void saveCustomerListToFile() throws Exception {
        List<String> StringObjects = customerList.stream().map(String::valueOf).collect(Collectors.toList());
        String data = String.join("\n", StringObjects);
        fileManager.saveDataToFile(data);
    }

    // ----------------------------------------------------------
    @Override
    public List<Customer> search(Predicate<Customer> predicate) throws Exception {
        return customerList.stream().filter(c -> predicate.test(c)).collect(Collectors.toList());
    }

}