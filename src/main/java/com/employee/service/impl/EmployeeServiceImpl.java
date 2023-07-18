package com.employee.service.impl;


import com.employee.entities.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmailService;
import com.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }


    public String addEmployee(Employee employee) {
        // Add employee to the database
        String id = UUID.randomUUID().toString();
        employee.setId(id);
        Employee savedEmployee = employeeRepository.save(employee);

        // Send email to Level 1 manager
        String managerEmail = getLevel1ManagerEmail(employee.getReportsTo());
        emailService.sendNewEmployeeEmail(managerEmail, savedEmployee);


        return id;
    }

    private String getLevel1ManagerEmail(String employeeId) {

        String email = employeeRepository.findById(employeeId).get().getEmail();
        System.out.println(email);
        return email;

    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    @Override
    public boolean deleteEmployeeById(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            employeeRepository.deleteById(id);
            return true; // Employee deleted successfully
        } else {
            return false; // Employee not found
        }
    }


    @Override
    public boolean updateEmployeeById(String id, Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setEmployeeName(updatedEmployee.getEmployeeName());
            employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setReportsTo(updatedEmployee.getReportsTo());
            employee.setProfileImage(updatedEmployee.getProfileImage());
            employeeRepository.save(employee);
            return true;
        }else {
            return false;
        }
    }



    public Optional<Employee> getNthLevelManager(String employeeId, int level) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            String reportsTo = employee.get().getReportsTo();
            return getNthLevelManagerRecursive(reportsTo, level - 1);
        }
        return Optional.empty();
    }

    private Optional<Employee> getNthLevelManagerRecursive(String employeeId, int level) {
        if (level < 0) {
            return Optional.empty();
        }

        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            String reportsTo = employee.get().getReportsTo();
            if (reportsTo != null) {
                if (level == 0) {
                    return employee;
                } else {
                    return getNthLevelManagerRecursive(reportsTo, level - 1);
                }
            }
        }

        return Optional.empty();
    }
}
