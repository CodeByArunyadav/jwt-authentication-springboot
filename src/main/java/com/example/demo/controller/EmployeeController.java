package com.example.demo.controller;

import com.example.demo.DTO.EmployeeDepartmentDTO;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeJoinService;
import com.example.demo.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
    private EmployeeService service;
	@Autowired
	private EmployeeJoinService joinService;

	/*
	 * public EmployeeController(EmployeeService service) { this.service = service;
	 * }
	 */

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return service.save(employee);
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Employee update(
            @PathVariable Long id,
            @RequestBody Employee employee
    ) {
        return service.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Employee deleted successfully";
    }
    
    @GetMapping("/department-details")
    public List<EmployeeDepartmentDTO>
    getEmployeeDepartmentDetails() {

        return joinService
                .getEmployeeDepartmentDetails();
    }
}