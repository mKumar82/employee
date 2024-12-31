package com.employee.controllers;

import com.employee.entities.Employee;
import com.employee.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee) {
        String id = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public Page<Employee> getAllEmployees(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "4") @Valid @Min(1) int size,
            @RequestParam(defaultValue = "employeeName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Sort.Order order = new Sort.Order(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return employeeService.getAllEmployees(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable @NotBlank String id) {
        boolean isDeleted = employeeService.deleteEmployeeById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable @NotBlank String id, @Valid @RequestBody Employee updatedEmployee) {
        boolean isUpdated = employeeService.updateEmployeeById(id, updatedEmployee);
        if (isUpdated) {
            return ResponseEntity.ok("Employee updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

    }

    @GetMapping("/{employeeId}/managers/{level}")
    public ResponseEntity<Employee> getNthLevelManager(@Validated @PathVariable @NotBlank String employeeId, @PathVariable @Valid @Min(1) int level) {
        System.out.println(employeeId);
        Optional<Employee> nthLevelManager = employeeService.getNthLevelManager(employeeId, level);
        return nthLevelManager.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
