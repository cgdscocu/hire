package com.example.hire.repository;

import com.example.hire.entity.QuestionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRuleRepository extends JpaRepository<QuestionRule, Long> {
    List<QuestionRule> findByQuestionId(Long questionId);
}


