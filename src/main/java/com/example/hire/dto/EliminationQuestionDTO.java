package com.example.hire.dto;

import com.example.hire.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EliminationQuestionDTO {
    
    private Long id;
    private String questionText;
    private AnswerType answerType;
    private boolean active;
    private Integer displayOrder;
    private Boolean isMustHave;
    private Long projectId;
    private Long processId;
    private List<QuestionOptionDTO> options;
    private List<QuestionRuleDTO> rules;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
