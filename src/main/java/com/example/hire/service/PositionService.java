package com.example.hire.service;

import com.example.hire.entity.Position;
import com.example.hire.exception.ValidationException;
import com.example.hire.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Position oluştur - case-insensitive duplicate kontrolü ile
     */
    public Position createPosition(Position position) {
        // Case-insensitive duplicate kontrolü
        if (positionRepository.existsByPositionNameIgnoreCase(position.getPositionName())) {
            throw new ValidationException("Bu pozisyon zaten mevcut: " + position.getPositionName());
        }

        // Timestamp'leri set et
        position.setCreatedDate(LocalDateTime.now());
        position.setUpdatedDate(LocalDateTime.now());

        return positionRepository.save(position);
    }

    /**
     * Tüm pozisyonları getir
     */
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    /**
     * Position name ile arama (case-insensitive)
     */
    public Position findByPositionName(String positionName) {
        return positionRepository.findByPositionNameIgnoreCase(positionName);
    }

    /**
     * Position name kontrolü (case-insensitive)
     */
    public boolean existsByPositionName(String positionName) {
        return positionRepository.existsByPositionNameIgnoreCase(positionName);
    }
}
