package com.example.hire.service;

import com.example.hire.dto.ProcessDateDTO;
import com.example.hire.entity.Process;
import com.example.hire.entity.Project;
import com.example.hire.enums.ProcessStatus;
import com.example.hire.enums.ProcessType;
import com.example.hire.repository.ProcessRepository;
import com.example.hire.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProcessRepository processRepository;
    private final ProcessValidationService processValidationService;

    public ProjectService(ProjectRepository projectRepository, 
                         ProcessRepository processRepository, 
                         ProcessValidationService processValidationService) {
        this.projectRepository = projectRepository;
        this.processRepository = processRepository;
        this.processValidationService = processValidationService;
    }

    @Transactional
    public Project createProject(Project project, List<ProcessDateDTO> processDates) {
        processValidationService.validateProcessDates(processDates);
        project.setCreatedDate(LocalDateTime.now());
        project.setUpdatedDate(LocalDateTime.now());
        Project savedProject = projectRepository.save(project);
        createProcessesWithDates(savedProject, processDates);

        return savedProject;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    private void createProcessesWithDates(Project project, List<ProcessDateDTO> processDates) {
        for (ProcessDateDTO processDateDTO : processDates) {
            Process process = new Process();
            process.setProcessType(processDateDTO.getProcessType());
            process.setProject(project);
            process.setPosition(project.getPosition());
            process.setStatus(ProcessStatus.PENDING);
            process.setPlannedStartDate(processDateDTO.getPlannedStartDate());
            process.setPlannedEndDate(processDateDTO.getPlannedEndDate());
            process.setCreatedDate(LocalDateTime.now());
            process.setUpdatedDate(LocalDateTime.now());

            processRepository.save(process);
        }
    }
}
