package com.example.hire.mapper;

import com.example.hire.dto.ProjectDTO;
import com.example.hire.dto.ProcessDateDTO;
import com.example.hire.entity.Project;
import com.example.hire.entity.Position;
import com.example.hire.entity.Process;
import com.example.hire.enums.ProcessType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {
    
    public ProjectDTO toDTO(Project project) {
        if (project == null) {
            return null;
        }
        
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setProjectName(project.getProjectName());
        dto.setStatus(project.getStatus());
        dto.setPositionId(project.getPosition() != null ? project.getPosition().getId() : null);
        
        if (project.getProcesses() != null) {
            List<ProcessDateDTO> processDates = project.getProcesses().stream()
                .map(process -> {
                    ProcessDateDTO processDateDTO = new ProcessDateDTO();
                    processDateDTO.setProcessType(process.getProcessType());
                    processDateDTO.setPlannedStartDate(process.getPlannedStartDate());
                    processDateDTO.setPlannedEndDate(process.getPlannedEndDate());
                    return processDateDTO;
                })
                .collect(Collectors.toList());
            dto.setProcessDates(processDates);
        }
        
        return dto;
    }
    
    public Project toEntity(ProjectDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Project project = new Project();
        project.setProjectName(dto.getProjectName());
        project.setStatus(dto.getStatus());
        
        if (dto.getPositionId() != null) {
            Position position = new Position();
            position.setId(dto.getPositionId());
            project.setPosition(position);
        }
        
        return project;
    }
}
