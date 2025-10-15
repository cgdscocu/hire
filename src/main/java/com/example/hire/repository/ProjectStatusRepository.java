package com.example.hire.repository;

import com.example.hire.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    
    Optional<ProjectStatus> findByCode(String code);
    
    List<ProjectStatus> findByIsActive(Boolean isActive);
    
    @Query("SELECT ps FROM ProjectStatus ps WHERE ps.isActive = true ORDER BY ps.sortOrder ASC")
    List<ProjectStatus> findActiveStatusesOrdered();
    
    List<ProjectStatus> findAllByOrderBySortOrderAsc();
}
