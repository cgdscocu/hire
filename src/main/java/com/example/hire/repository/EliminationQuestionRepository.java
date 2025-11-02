package com.example.hire.repository;

import com.example.hire.entity.EliminationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EliminationQuestionRepository extends JpaRepository<EliminationQuestion, Long> {
    List<EliminationQuestion> findByActiveTrueOrderByDisplayOrderAsc();
    
    // Proje ve süreç bazlı sorgular
    List<EliminationQuestion> findByProject_IdAndProcess_Id(Long projectId, Long processId);
    List<EliminationQuestion> findByProject_Id(Long projectId);
    List<EliminationQuestion> findByProcess_Id(Long processId);
    List<EliminationQuestion> findByProject_IdAndProcess_IdAndActiveTrueOrderByDisplayOrderAsc(Long projectId, Long processId);
}


