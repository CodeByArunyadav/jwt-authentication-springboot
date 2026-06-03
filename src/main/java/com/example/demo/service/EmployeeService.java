package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	private final EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public Employee save(Employee employee) {
		return repository.save(employee);
	}

	public List<Employee> getAll() {
		return repository.findAll();
	}

	public Employee getById(Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	public Employee update(Long id, Employee employee) {
		Employee existing = repository.findById(id).orElseThrow();

		existing.setEmpData(employee.getEmpData());

		return repository.save(existing);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}