package com.example.demo.backend.repository;

import com.example.demo.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long>  {
    List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
}
