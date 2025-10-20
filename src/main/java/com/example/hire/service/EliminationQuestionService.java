package com.example.hire.service;

import com.example.hire.entity.EliminationQuestion;

import java.util.List;

public interface EliminationQuestionService {
    List<EliminationQuestion> getActiveQuestions();
    EliminationQuestion createQuestion(EliminationQuestion question);
    void deleteQuestion(Long id);
}


