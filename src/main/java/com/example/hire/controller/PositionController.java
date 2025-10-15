package com.example.hire.controller;

import com.example.hire.entity.Position;
import com.example.hire.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "*")
public class PositionController {
    
    @Autowired
    private PositionRepository positionRepository;
    
    // Tüm pozisyonları getir
    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        return ResponseEntity.ok(positions);
    }
    
    // Yeni pozisyon oluştur
    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        position.setCreatedDate(LocalDateTime.now());
        position.setUpdatedDate(LocalDateTime.now());
        Position savedPosition = positionRepository.save(position);
        return ResponseEntity.ok(savedPosition);
    }
    
    
    // Belirli departmana ait pozisyonları getir
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Position>> getPositionsByDepartment(@PathVariable String department) {
        List<Position> positions = positionRepository.findByDepartment(department);
        return ResponseEntity.ok(positions);
    }
    
}

