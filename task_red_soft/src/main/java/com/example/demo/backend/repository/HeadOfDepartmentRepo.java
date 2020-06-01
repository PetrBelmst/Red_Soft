package com.example.demo.backend.repository;

import com.example.demo.backend.model.HeadOfDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeadOfDepartmentRepo extends JpaRepository<HeadOfDepartment, Long> {
    List<HeadOfDepartment> findByLastNameStartsWithIgnoreCase(String lastName);
}
