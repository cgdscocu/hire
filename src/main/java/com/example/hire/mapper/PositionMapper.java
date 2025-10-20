package com.example.hire.mapper;

import com.example.hire.dto.PositionDTO;
import com.example.hire.entity.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {
    
    public PositionDTO toDTO(Position position) {
        if (position == null) {
            return null;
        }
        
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setPositionName(position.getPositionName());
        return dto;
    }
    
    public Position toEntity(PositionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Position position = new Position();
        // ID'yi set etme - yeni entity için null olmalı
        position.setPositionName(dto.getPositionName());
        return position;
    }
}
