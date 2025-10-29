package com.example.hire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmitRequestDTO {
    private PersonalInfo personalInfo;
    private List<Answer> answers;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonalInfo {
        private String name;
        private String email;
        private String phone;
        private String cvUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Answer {
        private Long questionId;
        private String value;
    }
}


