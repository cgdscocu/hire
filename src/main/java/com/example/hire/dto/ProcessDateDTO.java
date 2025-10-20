package com.example.hire.dto;

import com.example.hire.enums.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDateDTO {
    private ProcessType processType;
    private LocalDateTime plannedStartDate;
    private LocalDateTime plannedEndDate;
}
