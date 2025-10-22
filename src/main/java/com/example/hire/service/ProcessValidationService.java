package com.example.hire.service;

import com.example.hire.dto.ProcessDateDTO;
import com.example.hire.enums.ProcessType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Service
public class ProcessValidationService {

    /**
     * Process dates validasyonu yapar
     */
    public void validateProcessDates(List<ProcessDateDTO> processDates) {
        if (processDates == null || processDates.isEmpty()) {
            throw new IllegalArgumentException("Process dates cannot be null or empty");
        }

        // 1. 4 süreç olmalı
        validateAllProcessTypesPresent(processDates);

        // 2. Her süreç için tarih validasyonu
        for (ProcessDateDTO processDate : processDates) {
            validateProcessDate(processDate);
        }

        // 3. Süreç sıralaması kontrolü
        validateProcessSequence(processDates);
    }

    /**
     * Tüm 4 süreç tipinin mevcut olduğunu kontrol eder
     */
    private void validateAllProcessTypesPresent(List<ProcessDateDTO> processDates) {
        Set<ProcessType> presentTypes = new HashSet<>();
        for (ProcessDateDTO processDate : processDates) {
            presentTypes.add(processDate.getProcessType());
        }

        ProcessType[] requiredTypes = {
            ProcessType.APPLICATION,
            ProcessType.ASSESSMENT,
            ProcessType.OFFER,
            ProcessType.ONBOARDING
        };

        for (ProcessType requiredType : requiredTypes) {
            if (!presentTypes.contains(requiredType)) {
                throw new IllegalArgumentException("Missing required process type: " + requiredType);
            }
        }
    }

    /**
     * Tek bir süreç için tarih validasyonu
     */
    private void validateProcessDate(ProcessDateDTO processDate) {
        if (processDate.getPlannedStartDate() == null) {
            throw new IllegalArgumentException("Planned start date cannot be null for process: " + processDate.getProcessType());
        }

        if (processDate.getPlannedEndDate() == null) {
            throw new IllegalArgumentException("Planned end date cannot be null for process: " + processDate.getProcessType());
        }

        if (processDate.getPlannedEndDate().isBefore(processDate.getPlannedStartDate())) {
            throw new IllegalArgumentException("Planned end date cannot be before start date for process: " + processDate.getProcessType());
        }

        if (processDate.getPlannedEndDate().isEqual(processDate.getPlannedStartDate())) {
            throw new IllegalArgumentException("Planned end date cannot be same as start date for process: " + processDate.getProcessType());
        }
    }

    /**
     * Süreç sıralaması kontrolü
     */
    private void validateProcessSequence(List<ProcessDateDTO> processDates) {
        ProcessType[] expectedOrder = {
            ProcessType.APPLICATION,
            ProcessType.ASSESSMENT,
            ProcessType.OFFER,
            ProcessType.ONBOARDING
        };

        for (int i = 0; i < expectedOrder.length - 1; i++) {
            ProcessType currentType = expectedOrder[i];
            ProcessType nextType = expectedOrder[i + 1];

            LocalDateTime currentEndDate = getEndDateForType(processDates, currentType);
            LocalDateTime nextStartDate = getStartDateForType(processDates, nextType);

            if (currentEndDate != null && nextStartDate != null) {
                if (nextStartDate.isBefore(currentEndDate)) {
                    throw new IllegalArgumentException("Process " + nextType + " cannot start before " + currentType + " ends");
                }
            }
        }
    }

    private LocalDateTime getEndDateForType(List<ProcessDateDTO> processDates, ProcessType type) {
        return processDates.stream()
            .filter(pd -> pd.getProcessType() == type)
            .findFirst()
            .map(ProcessDateDTO::getPlannedEndDate)
            .orElse(null);
    }

    private LocalDateTime getStartDateForType(List<ProcessDateDTO> processDates, ProcessType type) {
        return processDates.stream()
            .filter(pd -> pd.getProcessType() == type)
            .findFirst()
            .map(ProcessDateDTO::getPlannedStartDate)
            .orElse(null);
    }
}