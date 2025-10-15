package com.example.hire.repository;

import com.example.hire.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    List<Position> findByDepartment(String department);
}

