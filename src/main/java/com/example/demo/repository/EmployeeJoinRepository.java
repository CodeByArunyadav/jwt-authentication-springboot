package com.example.demo.repository;

import com.example.demo.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeJoinRepository
        extends JpaRepository<Employee, Long> {

    @Query(value = """

        SELECT

            e.emp_data->>'name' AS employee_name,

            e.emp_data->>'department' AS department_name,

            d.location

        FROM employees e

        JOIN departments d

        ON e.emp_data->>'department' = d.dept_name

        """,

        nativeQuery = true)

    List<Object[]> getEmployeeDepartmentDetails();
}