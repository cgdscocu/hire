package com.example.hire.repository;

import com.example.hire.entity.ProcessStep;
import com.example.hire.enums.ProcessStepStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProcessStepRepository extends JpaRepository<ProcessStep, Long> {
    
    List<ProcessStep> findByProcessId(Long processId);
    
    List<ProcessStep> findByStatus(ProcessStepStatus status);
    
    @Query("SELECT ps FROM ProcessStep ps WHERE ps.process.id = :processId ORDER BY ps.stepOrder ASC")
    List<ProcessStep> findByProcessIdOrderByStepOrder(Long processId);
    
    List<ProcessStep> findByDueDateBeforeAndStatusNot(LocalDateTime date, ProcessStepStatus status);
    
    @Query("SELECT ps FROM ProcessStep ps WHERE ps.dueDate < :date AND ps.status != :status")
    List<ProcessStep> findOverdueSteps(LocalDateTime date, ProcessStepStatus status);
    
    List<ProcessStep> findByProcessIdAndStatus(Long processId, ProcessStepStatus status);
}
