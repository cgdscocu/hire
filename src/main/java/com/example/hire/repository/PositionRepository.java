package com.example.hire.repository;

import com.example.hire.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    // Case-insensitive position name kontrolü
    boolean existsByPositionNameIgnoreCase(String positionName);
    
    // Case-insensitive position name ile arama
    Position findByPositionNameIgnoreCase(String positionName);
}

