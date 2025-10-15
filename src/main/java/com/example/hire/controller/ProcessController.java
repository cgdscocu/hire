package com.example.hire.controller;

import com.example.hire.entity.Process;
import com.example.hire.enums.ProcessStatus;
import com.example.hire.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processes")
@CrossOrigin(origins = "*")
public class ProcessController {
    
    @Autowired
    private ProcessRepository processRepository;
    
    // Tüm süreçleri getir (süreç ekranı için)
    @GetMapping
    public ResponseEntity<List<Process>> getAllProcesses() {
        List<Process> processes = processRepository.findAll();
        return ResponseEntity.ok(processes);
    }
    
    // Belirli bir projeye ait süreçleri getir
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Process>> getProcessesByProject(@PathVariable Long projectId) {
        List<Process> processes = processRepository.findByProjectId(projectId);
        return ResponseEntity.ok(processes);
    }
    
    // Belirli bir pozisyona ait süreçleri getir
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<Process>> getProcessesByPosition(@PathVariable Long positionId) {
        List<Process> processes = processRepository.findByPositionId(positionId);
        return ResponseEntity.ok(processes);
    }
    
    // Belirli proje ve pozisyona ait süreçleri getir
    @GetMapping("/project/{projectId}/position/{positionId}")
    public ResponseEntity<List<Process>> getProcessesByProjectAndPosition(
            @PathVariable Long projectId, 
            @PathVariable Long positionId) {
        List<Process> processes = processRepository.findByProjectIdAndPositionId(projectId, positionId);
        return ResponseEntity.ok(processes);
    }
    
    // Süreç durumunu güncelle
    @PutMapping("/{id}/status")
    public ResponseEntity<Process> updateProcessStatus(
            @PathVariable Long id, 
            @RequestBody ProcessStatus status) {
        Process process = processRepository.findById(id).orElse(null);
        if (process != null) {
            process.setStatus(status);
            process.setUpdatedDate(java.time.LocalDateTime.now());
            Process updatedProcess = processRepository.save(process);
            return ResponseEntity.ok(updatedProcess);
        }
        return ResponseEntity.notFound().build();
    }
}

