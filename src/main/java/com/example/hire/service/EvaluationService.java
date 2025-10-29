package com.example.hire.service;

import com.example.hire.dto.FormSubmitRequestDTO;
import com.example.hire.entity.EliminationQuestion;
import com.example.hire.enums.AnswerType;
import com.example.hire.repository.EliminationQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationService {

    private final EliminationQuestionRepository questionRepository;

    public EvaluationService(EliminationQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public boolean isEliminatedByMustHave(Long projectId, Long processId, List<FormSubmitRequestDTO.Answer> answers) {
        Map<Long, String> answerMap = new HashMap<>();
        for (FormSubmitRequestDTO.Answer a : answers) {
            answerMap.put(a.getQuestionId(), a.getValue());
        }

        List<EliminationQuestion> mustHaveQuestions =
            questionRepository.findByProjectIdAndProcessIdAndActiveTrueOrderByDisplayOrderAsc(projectId, processId)
                .stream().filter(q -> Boolean.TRUE.equals(q.getIsMustHave())).toList();

        for (EliminationQuestion q : mustHaveQuestions) {
            String val = answerMap.get(q.getId());
            if (!evaluate(q, val)) {
                return true; // failed a must-have
            }
        }
        return false;
    }

    private boolean evaluate(EliminationQuestion q, String value) {
        if (value == null) return false;
        if (q.getAnswerType() == AnswerType.YES_NO) {
            return Boolean.parseBoolean(value);
        }
        // SELECT / VALUE_RANGE: basit örnek - kurallar ayrıntısı ileride
        return true;
    }
}


