package com.example.hire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRuleDTO {
    
    private Long id;
    private String ruleCode;
    private String operator;
    private String targetValue;
}
