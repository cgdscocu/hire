package com.example.hire.controller;

import com.example.hire.dto.PositionDTO;
import com.example.hire.entity.Position;
import com.example.hire.mapper.PositionMapper;
import com.example.hire.repository.PositionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "*")
public class PositionController {
    
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    
    public PositionController(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }
    
    // Tüm pozisyonları getir
    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        List<PositionDTO> positionDTOs = positions.stream()
            .map(positionMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(positionDTOs);
    }
    
    // Yeni pozisyon oluştur
    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        Position position = positionMapper.toEntity(positionDTO);
        position.setCreatedDate(LocalDateTime.now());
        position.setUpdatedDate(LocalDateTime.now());
        Position savedPosition = positionRepository.save(position);
        PositionDTO savedDTO = positionMapper.toDTO(savedPosition);
        return ResponseEntity.ok(savedDTO);
    }
}