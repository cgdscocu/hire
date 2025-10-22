package com.example.hire.service;

import com.example.hire.entity.ApplicationDetails;
import com.example.hire.entity.Process;
import com.example.hire.entity.ProcessStep;
import com.example.hire.enums.ProcessType;
import com.example.hire.exception.BusinessException;
import com.example.hire.exception.NotFoundException;
import com.example.hire.repository.ApplicationDetailsRepository;
import com.example.hire.repository.ProcessRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationFormService {

    private ApplicationDetailsRepository applicationDetailsRepository;

    private ProcessRepository processRepository;

    /**
     * Application süreci için form token oluştur
     * Token geçerlilik süresi = Application sürecinin plannedEndDate
     */
    public String createApplicationFormToken(Long projectId) {
        // Application sürecini bul
        Process applicationProcess = findApplicationProcess(projectId);
        if (applicationProcess == null) {
            throw new NotFoundException("Application süreci bulunamadı! Project ID: " + projectId);
        }

        // Token oluştur
        String token = UUID.randomUUID().toString();
        
        // ApplicationDetails'i bul veya oluştur
        ApplicationDetails applicationDetails = findOrCreateApplicationDetails(applicationProcess);
        
        // Token ve geçerlilik tarihini set et
        applicationDetails.setFormToken(token);
        applicationDetails.setFormExpiryDate(applicationProcess.getPlannedEndDate());
        applicationDetails.setIsFormActive(true);
        
        // Kaydet
        applicationDetailsRepository.save(applicationDetails);
        
        return token;
    }

    /**
     * Token geçerliliğini kontrol et
     */
    public boolean isTokenValid(String token) {
        ApplicationDetails applicationDetails = applicationDetailsRepository.findByFormToken(token);
        
        if (applicationDetails == null) {
            return false; // Token bulunamadı
        }
        
        if (applicationDetails.getIsFormActive() == null || !applicationDetails.getIsFormActive()) {
            return false; // Form aktif değil
        }
        
        if (applicationDetails.getFormExpiryDate() == null) {
            return false; // Geçerlilik tarihi yok
        }
        
        // Geçerlilik tarihi geçmiş mi?
        return LocalDateTime.now().isBefore(applicationDetails.getFormExpiryDate());
    }

    /**
     * Form'u deaktive et
     */
    public void deactivateForm(String token) {
        ApplicationDetails applicationDetails = applicationDetailsRepository.findByFormToken(token);
        if (applicationDetails != null) {
            applicationDetails.setIsFormActive(false);
            applicationDetailsRepository.save(applicationDetails);
        }
    }

    /**
     * Application sürecini bul
     */
    private Process findApplicationProcess(Long projectId) {
        List<Process> processes = processRepository.findByProjectId(projectId);
        return processes.stream()
            .filter(p -> p.getProcessType() == ProcessType.APPLICATION)
            .findFirst()
            .orElse(null);
    }

    /**
     * ApplicationDetails'i bul veya oluştur
     */
    private ApplicationDetails findOrCreateApplicationDetails(Process applicationProcess) {
        // ProcessStep'i bul (Application sürecinin ilk adımı)
        ProcessStep applicationStep = applicationProcess.getProcessSteps().stream()
            .filter(step -> step.getStepName().contains("Application"))
            .findFirst()
            .orElse(null);
            
        if (applicationStep == null) {
            throw new BusinessException("Application sürecinin adımı bulunamadı! Process ID: " + applicationProcess.getId());
        }
        
        ApplicationDetails existingDetails = applicationDetailsRepository.findByProcessStep(applicationStep);
        
        if (existingDetails != null) {
            return existingDetails;
        }
        
        // Yeni oluştur
        ApplicationDetails newDetails = new ApplicationDetails();
        newDetails.setProcessStep(applicationStep);
        return applicationDetailsRepository.save(newDetails);
    }
}
