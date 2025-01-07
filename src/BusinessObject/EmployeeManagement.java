/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import Core.Entities.Employee;
import Core.Interfaces.IEmployeeDAO;
import Presentation.Menu;
import Utilities.DataInput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class EmployeeManagement {
    IEmployeeDAO employeeDAO;

    // ----------------------------------------------------------
    // Inject the EmployeeDAO object by IEmployeeDAO interface
    public EmployeeManagement(IEmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // ----------------------------------------------------------
    public void processMenuForEmployee() {
        boolean stop = true;
        try {
            do {
                Menu.print("******Employee Management******|1.Add Employee"
                        + "|2.Update Employee |3.Remove Employee"
                        + "|4.Search Employees |5.Print Employee List "
                        + "|6.Export to file |7.Back to main menu |Select:");
                int choice = Menu.getUserChoice();
                switch (choice) {
                    case 1 -> {
                        addNewEmployee();
                    }
                    case 2 -> {
                        updateEmployee();
                    }
                    case 3 -> {
                        deleteEmployee();
                    }
                    case 4 -> {
                        searchEmployee();
                    }
                    case 5 -> {
                        System.out.println(">>Employee List:");
                        printList(employeeDAO.getEmployees());
                    }
                    case 6 -> {
                        exportToFile();
                        System.out.println(">>The employee list has " + " successfully exported to file.");
                    }
                    case 7 -> stop = false;
                    default ->
                        System.out.println(">>Choice invalid");
                }
            } while (stop);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ----------------------------------------------------------
    public Employee inputEmployee() throws Exception {
        String id = DataInput.getString("Enter the id:");
        String name = DataInput.getString("Enter the name:");
        LocalDate dataOfBirth = DataInput.getDate("Enter the date of birth:");
        String email = DataInput.getString("Enter the email:");
        double salary = DataInput.getDoubleNumber("Enter the salary:");
        return new Employee(id, name, dataOfBirth, email, salary);
    }

    // ----------------------------------------------------------
    public void setNewEmployeeInfo(Employee employee) throws Exception {
        String name = DataInput.getString("Enter new name:");
        if (!name.isEmpty()) {
            employee.setName(name);
        }
        LocalDate dateOfBirth = DataInput.getDate("Enter new date of birth:");
        if (dateOfBirth != null) {
            employee.setDateOfBirth(dateOfBirth);
        }
        String email = DataInput.getString("Enter new email:");
        if (!email.isEmpty()) {
            employee.setEmail(email);
        }
        double salary = DataInput.getDoubleNumber("Enter new salary:");
        if (salary > 0) {
            employee.setSalary(salary);
        }
    }

    // ----------------------------------------------------------
    public void addNewEmployee() {
        try {
            Employee employee = inputEmployee();
            if (employeeDAO.getEmployeeById(employee.getId()) != null) {
                System.out.println(">>The employee already exists.");
                return;
            }
            employeeDAO.addEmployee(employee);
            System.out.println(">>The employee has added successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    public void updateEmployee() {
        try {
            String id = DataInput.getString("Enter employee id:");
            Employee employee = employeeDAO.getEmployeeById(id);
            if (employee == null) {
                System.out.println(">>The employee not found.");
                return;
            }
            setNewEmployeeInfo(employee);
            employeeDAO.updateEmployee(employee);
            System.out.println(">>The employee has updated successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    public void deleteEmployee() {
        try {
            String id = DataInput.getString("Enter employee id:");
            Employee employee = employeeDAO.getEmployeeById(id);
            if (employee == null) {
                System.out.println(">>The employee not found.");
                return;
            }
            employeeDAO.removeEmployee(employee);
            System.out.println(">>The employee has deleted successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    public void findEmployeeById() {
        Employee employee;
        try {
            String id = DataInput.getString("Enter employee id:");
            employee = employeeDAO.getEmployeeById(id);
            if (employee != null) {
                System.out.println(employee);
            } else {
                System.out.format("The employee id: %s not found.%n", id);
            }
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    public void searchEmployee() throws Exception {
        int choice;
        boolean stop = true;
        String value;
        try {
            do {
                Menu.print("1. Search by name | 2.Search by salary | 3.Back | Select:");
                choice = DataInput.getIntegerNumber();
                switch (choice) {
                    case 1 -> {
                        value = DataInput.getString("Enter the name:");
                        List<Employee> employees = searchEmployeeByName(value);
                        if (!employees.isEmpty()) {
                            printList(employees);
                        } else {
                            System.out.format("The employees with name:%s" + "not found.%n", value);
                        }
                    }
                    case 2 -> {
                        double salary = DataInput.getDoubleNumber("Enter the salary:");
                        List<Employee> employees = searchEmployeeBySalary(salary);
                        if (!employees.isEmpty()) {
                            printList(employees);
                        } else {
                            System.out.format(
                                    "The employees with salary" + "are greater than or equal to: %s not found.%n",
                                    String.valueOf(salary));
                        }
                    }
                    case 3 -> {
                        stop = false;
                    }
                    default -> {
                        System.out.println(">>Choice invalid");
                    }
                }
            } while (stop);
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    public void printList(List<Employee> employees) throws Exception {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.format("%-10s | %-20s | %-20s | %-19s | %s%n", "Employee Id", "Employee Name", "DateOfBirth",
                "Email", "Salary");
        System.out.println(String.join("", Collections.nCopies(95, "-")));
        for (Employee employee : employees) {
            System.out.format("%-11s | %-20s | %-20s | %-20s | %.2f%n", employee.getId(), employee.getName(),
                    employee.getDateOfBirth().format(formatters), employee.getEmail(), employee.getSalary());
        }
        System.out.println(String.join("", Collections.nCopies(95, "-")));
    }

    // ----------------------------------------------------------
    public void exportToFile() throws Exception {
        employeeDAO.saveEmployeeListToFile();
    }

    // ----------------------------------------------------------
    public List<Employee> searchEmployeeByName(String value) throws Exception {
        Predicate<Employee> predicate = p -> p.getName().toLowerCase().contains(value.toLowerCase());
        List<Employee> employees = employeeDAO.search(predicate);
        employees.sort((Employee e1, Employee e2) -> {
            // sort by name ascending order
            // if two employees have the same name, sort by salary descending order
            if (e1.getName().compareToIgnoreCase(e2.getName()) > 0) {
                return 1;
            } else if (e2.getName().compareToIgnoreCase(e1.getName()) == 0) {
                return Double.compare(e2.getSalary(), e1.getSalary());
            } else
                return -1;
        });
        return employees;
    }

    // ----------------------------------------------------------
    public List<Employee> searchEmployeeBySalary(double value) throws Exception {
        Predicate<Employee> predicate = (e -> e.getSalary() >= value);
        List<Employee> employees = employeeDAO.search(predicate);
        // sort by salary descending order if two employees have the same salary,
        // sort by name ascending order
        employees.sort((Employee e1, Employee e2) -> {
            if (Double.compare(e2.getSalary(), e1.getSalary()) == 1) {
                return 1;
            } else if (Double.compare(e1.getSalary(), e2.getSalary()) == 0) {
                return e1.getName().compareToIgnoreCase(e2.getName());
            } else
                return -1;
        });
        return employees;
    }
}
