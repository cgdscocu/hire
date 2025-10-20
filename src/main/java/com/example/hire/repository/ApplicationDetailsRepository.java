package com.example.hire.repository;

import com.example.hire.entity.ApplicationDetails;
import com.example.hire.entity.ProcessStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationDetailsRepository extends JpaRepository<ApplicationDetails, Long> {
    
    ApplicationDetails findByFormToken(String formToken);
    
    ApplicationDetails findByProcessStep(ProcessStep processStep);
    
    Optional<ApplicationDetails> findByProcessStepId(Long processStepId);
}
