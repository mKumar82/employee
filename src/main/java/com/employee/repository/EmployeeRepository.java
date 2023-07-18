package com.employee.repository;

import com.employee.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee,String> {

//    List<Employee> findByEmployeeId(String id);

}
