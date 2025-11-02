package com.example.hire.dto;

import com.example.hire.enums.EvaluationOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRuleDTO {
    
    private Long id;
    private String ruleCode;
    private EvaluationOperator operator;
    private String targetValue;
}
