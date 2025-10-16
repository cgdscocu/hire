package com.example.hire.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApplicationDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_step_id", nullable = false)
    private ProcessStep processStep;
    
    @Column(name = "cv_score")
    private Integer cvScore; // 1-10 arası puan
    
    @Column(name = "cv_notes", length = 1000)
    private String cvNotes;
    
    // Başvuru sahibinin referans bilgisi (referans kişi/kurum vs.)
    @Column(name = "reference_info", length = 255)
    private String referenceInfo;
    
    @Column(name = "additional_notes", length = 1000)
    private String additionalNotes;
    
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
