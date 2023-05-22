package com.example.homework2_13.controller;

import com.example.homework2_13.Employee;
import com.example.homework2_13.exception.EmployeeNotFoundException;
import com.example.homework2_13.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/add")
    public Employee add(@RequestParam String firstName, @RequestParam String lastName,
                        @RequestParam Integer department, @RequestParam Integer wage) {
        return employeeService.add(firstName, lastName, department, wage);
    }
    @GetMapping("/remove")
    public Employee remove(@RequestParam String firstName, @RequestParam String lastName,
                           @RequestParam Integer department, @RequestParam Integer wage) {
        return employeeService.remove(firstName, lastName, department, wage);
    }
    @GetMapping("/find")
    public Employee find(@RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam Integer department, @RequestParam Integer wage) {
        return employeeService.find(firstName, lastName, department, wage);
    }
    @GetMapping
    public List<Employee> list() {
        return employeeService.list();
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> employeeNotFoundExceptionHandler(EmployeeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Сотрудник " + e.getEmployee() + " не найден");
    }
}
