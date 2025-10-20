package com.example.hire.service;

import com.example.hire.dto.ProcessDateDTO;
import com.example.hire.entity.Process;
import com.example.hire.entity.Project;
import com.example.hire.enums.ProcessStatus;
import com.example.hire.enums.ProcessType;
import com.example.hire.repository.ProcessRepository;
import com.example.hire.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessValidationService processValidationService;

    /**
     * Project oluştur ve 4 ana süreci otomatik oluştur
     */
    @Transactional
    public Project createProject(Project project, List<ProcessDateDTO> processDates) {
        // Validasyon yap
        processValidationService.validateProcessDates(processDates);
        
        // Project'i kaydet
        project.setCreatedDate(LocalDateTime.now());
        project.setUpdatedDate(LocalDateTime.now());
        Project savedProject = projectRepository.save(project);

        // 4 ana süreci belirtilen tarihlerle oluştur
        createProcessesWithDates(savedProject, processDates);

        return savedProject;
    }

    /**
     * Tüm projeleri getir
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * 4 ana süreci belirtilen tarihlerle oluştur
     */
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
