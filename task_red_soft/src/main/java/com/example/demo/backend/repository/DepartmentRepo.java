package com.example.demo.backend.repository;

import com.example.demo.backend.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Department findByNameStartsWithIgnoreCase(String name);

}
