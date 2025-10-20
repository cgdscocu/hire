package com.example.hire.service.impl;

import com.example.hire.entity.EliminationQuestion;
import com.example.hire.entity.QuestionOption;
import com.example.hire.entity.QuestionRule;
import com.example.hire.repository.EliminationQuestionRepository;
import com.example.hire.service.EliminationQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EliminationQuestionServiceImpl implements EliminationQuestionService {

    private final EliminationQuestionRepository eliminationQuestionRepository;

    public EliminationQuestionServiceImpl(EliminationQuestionRepository eliminationQuestionRepository) {
        this.eliminationQuestionRepository = eliminationQuestionRepository;
    }

    @Override
    public List<EliminationQuestion> getActiveQuestions() {
        return eliminationQuestionRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    public EliminationQuestion createQuestion(EliminationQuestion question) {
        if (question.getOptions() != null) {
            for (QuestionOption opt : question.getOptions()) {
                opt.setQuestion(question);
            }
        }
        if (question.getRules() != null) {
            for (QuestionRule rule : question.getRules()) {
                rule.setQuestion(question);
            }
        }
        return eliminationQuestionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        eliminationQuestionRepository.deleteById(id);
    }
}


