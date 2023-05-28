package com.example.homework2_13.controller;

import com.example.homework2_13.Employee;
import com.example.homework2_13.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @GetMapping("/all")
    public List<Employee> getAllInDepart(int departmentId){
        return DepartmentService.getAllInDepart(departmentId);
    }
    @GetMapping("/departments/all")
    public List<Employee> getAllByDepart(@RequestParam("departmentId")Integer id) {
        return DepartmentService.getAllByDepart();
    }
}
