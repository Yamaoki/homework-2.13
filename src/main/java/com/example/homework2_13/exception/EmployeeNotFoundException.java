package com.example.homework2_13.exception;


import com.example.homework2_13.Employee;

public class EmployeeNotFoundException extends RuntimeException {
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }
}
