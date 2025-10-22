package com.example.hire.repository;

import com.example.hire.entity.EliminationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EliminationQuestionRepository extends JpaRepository<EliminationQuestion, Long> {
    List<EliminationQuestion> findByActiveTrueOrderByDisplayOrderAsc();
    
    // Proje ve süreç bazlı sorgular
    List<EliminationQuestion> findByProjectIdAndProcessId(Long projectId, Long processId);
    List<EliminationQuestion> findByProjectId(Long projectId);
    List<EliminationQuestion> findByProcessId(Long processId);
    List<EliminationQuestion> findByProjectIdAndProcessIdAndActiveTrueOrderByDisplayOrderAsc(Long projectId, Long processId);
}


