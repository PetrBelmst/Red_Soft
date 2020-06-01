package com.example.demo.backend.repository;

import com.example.demo.backend.model.HeadOfDepartment;
import com.example.demo.backend.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepo extends JpaRepository<Position, Long> {
    Position findByPositionName(String name);
}
