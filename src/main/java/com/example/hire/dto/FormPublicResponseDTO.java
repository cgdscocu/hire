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
public class FormPublicResponseDTO {
    private Long projectId;
    private String projectName;
    private String process;
    private LocalDateTime expiresAt;
    private PersonalInfoConfig personalInfo;
    private List<QuestionDTO> questions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonalInfoConfig {
        private boolean name;
        private boolean email;
        private boolean phone;
        private boolean cv;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDTO {
        private Long id;
        private String text;
        private AnswerType answerType;
        private boolean mustHave;
        private List<OptionDTO> options;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionDTO {
        private String label;
        private String value;
        private Integer rank;
    }
}


