/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import BusinessObject.CustomerManagement;
import BusinessObject.EmployeeManagement;
import Core.Interfaces.ICustomerDAO;
import Core.Interfaces.IEmployeeDAO;
import Utilities.DataInput;
import java.util.Arrays;

public class Menu {
    public static void print(String str) {
        var menuList = Arrays.asList(str.split("\\|"));
        menuList.forEach(menuItem -> {
            if (menuItem.equalsIgnoreCase("Select:")) {
                System.out.print(menuItem);
            } else {
                System.out.println(menuItem);
            }
        });
    }

    // -------------------------------------------------------------------
    public static int getUserChoice() {
        int number = 0;
        try {
            number = DataInput.getIntegerNumber();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return number;
    }

    // -------------------------------------------------------------------
    public static void manageEmployee(IEmployeeDAO service) {
        EmployeeManagement empMenu = new EmployeeManagement(service);
        empMenu.processMenuForEmployee();
    }

    // -------------------------------------------------------------------
    public static void manageCustomer(ICustomerDAO service) {
        CustomerManagement cusMenu = new CustomerManagement(service);
        cusMenu.processMenuForCustomer();
    }
}
