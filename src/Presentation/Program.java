/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Utilities.DataInput;
import DataObjects.CustomerDAO;
import DataObjects.EmployeeDAO;
import Core.Interfaces.ICustomerDAO;
import Core.Interfaces.IEmployeeDAO;

public class Program {
    public static void main(String[] args) {
        int choice;
        String employeeDataFile = "Employee.txt";
        String customerDataFile = "Customer.txt";
        try {
            do {
                System.out.println("******Main Menu******");
                Menu.print("1.Employee Management |2. Customer Management |3. Exit |Select:");
                choice = DataInput.getIntegerNumber();
                switch (choice) {
                    case 1 -> {
                        IEmployeeDAO employeeService = new EmployeeDAO(employeeDataFile);
                        Menu.manageEmployee(employeeService);
                    }
                    case 2 -> {
                        ICustomerDAO customerService = new CustomerDAO(customerDataFile);
                        Menu.manageCustomer(customerService);
                    }
                    default -> {
                        System.out.println("Good bye !");
                        System.exit(0);
                    }
                }
            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
