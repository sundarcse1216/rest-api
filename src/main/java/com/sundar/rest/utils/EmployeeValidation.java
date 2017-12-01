/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sundar.rest.utils;

import org.apache.log4j.Logger;

/**
 *
 * @author sundar
 * @since 2017-10-23
 * @modified 2017-10-23
 */
public class EmployeeValidation {

    private static final Logger log = Logger.getLogger(EmployeeValidation.class);

    public String ValidateInput(String empID, String name, int age, String gender, long salary, String location) {

        StringBuilder errorMessage = new StringBuilder();

        if (empID == null || empID.isEmpty()) {
            errorMessage.append("EmployeeID should not Null or Empty");
        }

        if (name != null && !name.isEmpty()) {
            if (name.length() < 6 || name.length() > 15) {
                if (errorMessage.length() != 0) {
                    errorMessage.append(",");
                }
                errorMessage.append("Employee Name length should be morethan five and lessthan 15");
            }
        } else {
            if (errorMessage.length() != 0) {
                errorMessage.append(",");
            }
            errorMessage.append("Employee Name should not Null or Empty");
        }

        if (age == 0) {
            if (errorMessage.length() != 0) {
                errorMessage.append(",");
            }
            errorMessage.append("Age should not Null");
        }

        if (gender != null && !gender.isEmpty()) {
            if (!"male".equalsIgnoreCase(gender) && !"female".equalsIgnoreCase(gender)) {
                if (errorMessage.length() != 0) {
                    errorMessage.append(",");
                }
                errorMessage.append("Gender is invalid");
            }
        } else {
            if (errorMessage.length() != 0) {
                errorMessage.append(",");
            }
            errorMessage.append("Gender should not Null");
        }

        if (salary == 0) {
            if (errorMessage.length() != 0) {
                errorMessage.append(",");
            }
            errorMessage.append("Salary should not Null");
        }

        if (location == null || location.isEmpty()) {
            if (errorMessage.length() != 0) {
                errorMessage.append(",");
            }
            errorMessage.append("Location should not Null");
        }

        return errorMessage.toString();
    }

    private boolean isAlphanumeric(String empID) {
        return empID.matches("[a-zA-Z0-9]");
    }
}
