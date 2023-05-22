package com.example.homework2_13.service;

import com.example.homework2_13.Employee;
import com.example.homework2_13.exception.EmployeeAlreadyAddedException;
import com.example.homework2_13.exception.EmployeeNotFoundException;
import com.example.homework2_13.exception.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private static final int SIZE=12;
    private final List<Employee> employees = new ArrayList<>(SIZE);
    public Employee add(String firstName, String lastName, Integer department, Integer wage) {
        Employee employee = new Employee(firstName, lastName, department, wage);
        if (employees.size() < SIZE) {
            for (Employee emp : employees) {
                if (emp.equals(employee)) {
                    throw new EmployeeAlreadyAddedException();
                }
            }
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }
    public Employee find(String firstName, String lastName, Integer department, Integer wage) {
        Employee employee = new Employee(firstName, lastName, department, wage);
        if (employees.contains(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }
    public Employee remove(String firstName, String lastName, Integer department, Integer wage) {
        Employee employee = new Employee(firstName, lastName, department, wage);
        if (employees.remove(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException();
    }

    public List<Employee> list() {
        return Collections.unmodifiableList(employees);
    }
    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }
}
