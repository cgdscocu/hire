package com.example.hire.dto;

import com.example.hire.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    
    private Long id;
    private String projectName;
    private ProjectStatus status;
    private Long positionId;
    private List<ProcessDateDTO> processDates;
}
