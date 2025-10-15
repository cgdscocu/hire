package com.example.hire.controller;

import com.example.hire.entity.Project;
import com.example.hire.entity.ProjectStatus;
import com.example.hire.repository.ProjectRepository;
import com.example.hire.repository.ProjectStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectStatusRepository projectStatusRepository;
    
    // Tüm projeleri getir
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }
    
    // Yeni proje oluştur
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        project.setCreatedDate(LocalDateTime.now());
        project.setUpdatedDate(LocalDateTime.now());
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.ok(savedProject);
    }
    
    // Proje durumunu güncelle
    @PutMapping("/{id}/status")
    public ResponseEntity<Project> updateProjectStatus(
            @PathVariable Long id, 
            @RequestBody Long statusId) {
        Project project = projectRepository.findById(id).orElse(null);
        ProjectStatus status = projectStatusRepository.findById(statusId).orElse(null);
        
        if (project != null && status != null) {
            project.setStatus(status);
            project.setUpdatedDate(LocalDateTime.now());
            Project updatedProject = projectRepository.save(project);
            return ResponseEntity.ok(updatedProject);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Belirli pozisyona ait projeleri getir
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<Project>> getProjectsByPosition(@PathVariable Long positionId) {
        List<Project> projects = projectRepository.findByPositionId(positionId);
        return ResponseEntity.ok(projects);
    }
    
    // Belirli durumdaki projeleri getir
    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable Long statusId) {
        ProjectStatus status = projectStatusRepository.findById(statusId).orElse(null);
        if (status != null) {
            List<Project> projects = projectRepository.findByStatus(status);
            return ResponseEntity.ok(projects);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Tüm proje durumlarını getir
    @GetMapping("/statuses")
    public ResponseEntity<List<ProjectStatus>> getAllProjectStatuses() {
        List<ProjectStatus> statuses = projectStatusRepository.findActiveStatusesOrdered();
        return ResponseEntity.ok(statuses);
    }
}

