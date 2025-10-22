package com.example.hire.controller;

import com.example.hire.dto.PositionDTO;
import com.example.hire.entity.Position;
import com.example.hire.mapper.PositionMapper;
import com.example.hire.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "*")
public class PositionController {
    
    private final PositionService positionService;
    private final PositionMapper positionMapper;
    
    public PositionController(PositionService positionService, PositionMapper positionMapper) {
        this.positionService = positionService;
        this.positionMapper = positionMapper;
    }
    
    // Tüm pozisyonları getir
    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        List<PositionDTO> positionDTOs = positions.stream()
            .map(positionMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(positionDTOs);
    }
    
    // Yeni pozisyon oluştur
    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        Position position = positionMapper.toEntity(positionDTO);
        Position savedPosition = positionService.createPosition(position);
        PositionDTO savedDTO = positionMapper.toDTO(savedPosition);
        return ResponseEntity.ok(savedDTO);
    }
    
    // Position name kontrolü (frontend için)
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkPositionExists(@RequestParam String name) {
        boolean exists = positionService.existsByPositionName(name);
        return ResponseEntity.ok(exists);
    }
}