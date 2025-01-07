/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Interfaces;

import Core.Entities.Employee;
import java.util.List;
import java.util.function.Predicate;

public interface IEmployeeDAO {
    List<Employee> getEmployees() throws Exception;

    Employee getEmployeeById(String id) throws Exception;

    void addEmployee(Employee employee) throws Exception;

    void updateEmployee(Employee employee) throws Exception;

    void removeEmployee(Employee employee) throws Exception;

    void saveEmployeeListToFile() throws Exception;

    List<Employee> search(Predicate<Employee> predicate) throws Exception;
    // ----------------------------------------------------------
    // More the methods here ....
}
