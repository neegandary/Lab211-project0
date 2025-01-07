/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import Core.Entities.Employee;
import Core.Interfaces.IEmployeeDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class EmployeeDAO implements IEmployeeDAO {
    private final List<Employee> employeeList = new ArrayList<>();
    private final FileManager fileManager;

    public EmployeeDAO(String fileName) throws Exception {
        this.fileManager = new FileManager(fileName);
        loadDataFromFile();

    }

    public void loadDataFromFile() throws Exception {
        String id, name, email;
        LocalDate dateOfBirth;
        double salary;
        try {
            employeeList.clear();
            List<String> empData = fileManager.readDataFromFile();
            for (String e : empData) {
                List<String> emp = Arrays.asList(e.split(","));
                id = emp.get(0).trim();
                name = emp.get(1).trim();
                dateOfBirth = LocalDate.parse(emp.get(2).trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                email = emp.get(3).trim();
                salary = Double.parseDouble(emp.get(4).trim());
                Employee newEmp = new Employee(id, name, dateOfBirth, email, salary);
                System.out.println(emp);
                employeeList.add(newEmp);
                if (employeeList.isEmpty()) {
                    throw new Exception("Employee list is empty.");
                }

            }
        } catch (Exception ex) {
            throw new Exception("Can not read data from file.Please check file again.");
        }
    }

    public List<Employee> getEmployees() throws Exception {
        Collections.sort(employeeList, (e1, e2) -> e1.getId().compareTo(e2.getId()));
        return employeeList;
    }

    public void addEmployee(Employee employee) throws Exception {
        employeeList.add(employee);
    }

    public void updateEmployee(Employee employee) throws Exception {
        Employee emp = getEmployeeById(employee.getId());
        if (emp != null) {
            emp.setName(employee.getName());
            emp.setEmail(employee.getEmail());
            emp.setSalary(employee.getSalary());
            emp.setDateOfBirth(employee.getDateOfBirth());
        }
    }

    public void removeEmployee(Employee employee) throws Exception {
        Employee emp = getEmployeeById(employee.getId());
        if (emp != null) {
            employeeList.remove(employee);
        }
    }

    public Employee getEmployeeById(String id) throws Exception {
        if (employeeList.isEmpty()) {
            getEmployees();
        }
        Employee employee = employeeList.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findAny().orElse(null);
        return employee;
    }

    public List<Employee> search(Predicate<Employee> predicate) throws Exception {
        return employeeList.stream().filter(employee -> predicate.test(employee)).collect(Collectors.toList());
    }

    @Override
    public void saveEmployeeListToFile() throws Exception {
        List<String> stringObjects = employeeList.stream().map(String::valueOf).collect(Collectors.toList());
        String data = String.join("\n", stringObjects);
        fileManager.saveDataToFile(data);
    }
}