package com.example.hire.service;

import com.example.hire.dto.FormSubmitRequestDTO;
import com.example.hire.entity.EliminationQuestion;
import com.example.hire.entity.QuestionRule;
import com.example.hire.enums.EvaluationOperator;
import com.example.hire.repository.EliminationQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationService {

    private final EliminationQuestionRepository questionRepository;
    private final RuleEvaluator ruleEvaluator;

    public EvaluationService(EliminationQuestionRepository questionRepository, 
                            RuleEvaluator ruleEvaluator) {
        this.questionRepository = questionRepository;
        this.ruleEvaluator = ruleEvaluator;
    }

    /**
     * Must-have soruları kontrol eder ve eğer herhangi biri geçilmezse true döner (elenmiş)
     */
    public boolean isEliminatedByMustHave(Long projectId, Long processId, List<FormSubmitRequestDTO.Answer> answers) {
        Map<Long, String> answerMap = new HashMap<>();
        for (FormSubmitRequestDTO.Answer a : answers) {
            answerMap.put(a.getQuestionId(), a.getValue());
        }

        List<EliminationQuestion> mustHaveQuestions =
            questionRepository.findByProject_IdAndProcess_IdAndActiveTrueOrderByDisplayOrderAsc(projectId, processId)
                .stream().filter(q -> Boolean.TRUE.equals(q.getIsMustHave())).toList();

        for (EliminationQuestion question : mustHaveQuestions) {
            String answerValue = answerMap.get(question.getId());
            if (!evaluateQuestion(question, answerValue)) {
                return true; // failed a must-have question
            }
        }
        return false;
    }

    /**
     * Bir soruyu ve kurallarını değerlendirir
     * Eğer sorunun kuralları varsa, tüm kurallar geçmeli (AND mantığı)
     * Eğer kural yoksa, sadece cevap verilmiş mi kontrol edilir
     */
    private boolean evaluateQuestion(EliminationQuestion question, String answerValue) {
        if (answerValue == null || answerValue.trim().isEmpty()) {
            return false;
        }

        // Eğer kural yoksa, sadece cevap verilmiş mi kontrol et
        if (question.getRules() == null || question.getRules().isEmpty()) {
            return true; // Kural yoksa, cevap verilmişse geçer
        }

        // Tüm kurallar geçmeli (AND mantığı)
        for (QuestionRule rule : question.getRules()) {
            if (!evaluateRule(rule, answerValue)) {
                return false; // Bir kural bile geçilmezse false
            }
        }
        
        return true; // Tüm kurallar geçildi
    }

    /**
     * Tek bir rule'u değerlendirir
     */
    private boolean evaluateRule(QuestionRule rule, String answerValue) {
        EvaluationOperator operator = rule.getOperator();
        String targetValue = rule.getTargetValue();
        
        // RuleEvaluator'a gönder
        return ruleEvaluator.evaluate(operator, answerValue, targetValue);
    }
}



