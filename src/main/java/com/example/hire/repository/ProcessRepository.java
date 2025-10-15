package com.example.hire.repository;

import com.example.hire.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    
    List<Process> findByProjectId(Long projectId);
    
    List<Process> findByPositionId(Long positionId);
    
    List<Process> findByProjectIdAndPositionId(Long projectId, Long positionId);
}

