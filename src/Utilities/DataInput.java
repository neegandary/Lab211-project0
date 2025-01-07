/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class DataInput {
    // ----------------------------------------------------------
    public static int getIntegerNumber(String displayMessage)
            throws Exception {
        int number = 0;
        System.out.print(displayMessage);
        number = getIntegerNumber();
        return number;
    }

    // ----------------------------------------------------------
    public static int getIntegerNumber() throws Exception {
        int number = 0;
        String strInput;
        strInput = getString();
        if (!DataValidation.checkStringWithFormat(strInput, "\\d{1,10}")) {
            throw new Exception("Data Valid");
        } else {
            number = Integer.parseInt(strInput);
        }
        return number;
    }

    // ----------------------------------------------------------
    public static double getDoubleNumber(String displayMessage) throws Exception {
        double number = 0;
        String strInput = getString(displayMessage);
        if (DataValidation.checkStringEmpty(strInput)) {
            if (!DataValidation.checkStringWithFormat(strInput, "[0-9]+[.]?[0-9]+")) {
                throw new Exception("Data invalid.");
            } else {
                number = Double.parseDouble(strInput);
            }
        }
        return number;
    }

    // ----------------------------------------------------------
    public static String getString(String displayMessage) {
        String strInput;
        System.out.print(displayMessage);
        strInput = getString();
        return strInput;
    }

    // ----------------------------------------------------------
    public static String getString() {
        String strInput;
        try (Scanner sc = new Scanner(System.in)) {
            strInput = sc.nextLine();
        }
        return strInput;
    }

    // ----------------------------------------------------------
    public static LocalDate getDate(String displayMessage) throws Exception {
        String strInput;
        LocalDate date = null;
        strInput = getString(displayMessage);
        if (DataValidation.checkStringEmpty(strInput)) {
            try {
                date = LocalDate.parse(strInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {
                throw new Exception("Date vinalid");
            }
        }
        return date;
    }
}