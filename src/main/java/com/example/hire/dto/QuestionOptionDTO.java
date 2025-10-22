package com.example.hire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionDTO {
    
    private Long id;
    private String label;
    private String value;
    private Integer rank;
}
