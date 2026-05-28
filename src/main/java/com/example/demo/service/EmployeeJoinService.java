package com.example.demo.service;

import com.example.demo.DTO.EmployeeDepartmentDTO;
import com.example.demo.repository.EmployeeJoinRepository;
import com.example.demo.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeJoinService {

    @Autowired
    private EmployeeJoinRepository employeeJoinRepository;

    public List<EmployeeDepartmentDTO>
    getEmployeeDepartmentDetails() {

        List<Object[]> rows =
                employeeJoinRepository
                        .getEmployeeDepartmentDetails();

        List<EmployeeDepartmentDTO> result =
                new ArrayList<>();

        for (Object[] row : rows) {

            result.add(

                new EmployeeDepartmentDTO(

                    row[0].toString(),

                    row[1].toString(),

                    row[2].toString()
                )
            );
        }

        return result;
    }
}