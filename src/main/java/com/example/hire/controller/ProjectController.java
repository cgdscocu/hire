package com.example.hire.controller;

import com.example.hire.dto.ProjectDTO;
import com.example.hire.entity.Project;
import com.example.hire.mapper.ProjectMapper;
import com.example.hire.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectDTOs = projects.stream()
            .map(projectMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(projectDTOs);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        Project createdProject = projectService.createProject(project, projectDTO.getProcessDates());
        ProjectDTO createdDTO = projectMapper.toDTO(createdProject);
        return ResponseEntity.ok(createdDTO);
    }
}