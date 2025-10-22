package com.example.hire.repository;

import com.example.hire.entity.Project;
import com.example.hire.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByPositionId(Long positionId);
    
    List<Project> findByStatus(ProjectStatus status);
}

