package com.employee.service;

import com.employee.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    String addEmployee(Employee employee);
    Page<Employee> getAllEmployees(Pageable pageable);
    boolean deleteEmployeeById(String id);
    boolean updateEmployeeById(String id, Employee updatedEmployee);

    Optional<Employee> getNthLevelManager(String employeeId, int level);
}
