/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Interfaces;

import Core.Entities.Customer;
import java.util.List;
import java.util.function.Predicate;

public interface ICustomerDAO {
    List<Customer> getCustomers() throws Exception;

    Customer getCustomerById(String id) throws Exception;

    void addCustomer(Customer customer) throws Exception;

    void updateCustomer(Customer customer) throws Exception;

    void removeCustomer(Customer customer) throws Exception;

    void saveCustomerListToFile() throws Exception;

    List<Customer> search(Predicate<Customer> predicate) throws Exception;
}
