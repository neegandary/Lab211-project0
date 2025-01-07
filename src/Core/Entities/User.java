/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Entities;

import Utilities.DataValidation;

public class User {
    // Fields
    protected String id;
    protected String name;

    public User() {
        id = "U000";
        name = "New User";
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id.toUpperCase();
    }

    public void setId(String id) throws Exception {
        this.id = id;
    }

    public String getName() {
        return toTiteCase(name);
    }

    public void setName(String name) throws Exception {
        if (!DataValidation.checkStringWithFormat(name, "[A-Za-z|\\s]{3,50}")) {
            throw new Exception("Name must be from 3 to 50 characters.");
        }
        this.name = toTiteCase(name);
    }

    public String toTiteCase(String value) {
        String s = " ";
        value = value.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] words = value.split(" ");
        for (String word : words) {
            char[] arr = word.toCharArray();
            arr[0] = Character.toUpperCase(arr[0]);
            s = s + new String(arr) + " ";
        }
        return s.trim();
    }

    @Override
    public String toString() {
        return String.format("%s , %s", id, name);
    }
}
