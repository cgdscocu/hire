package com.example.hire.entity;

import com.example.hire.enums.ProcessStatus;
import com.example.hire.enums.ProcessType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "processes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessType processType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessStatus status;
    
    @Column(name = "created_date", nullable = false)
    private java.time.LocalDateTime createdDate;
    
    @Column(name = "updated_date")
    private java.time.LocalDateTime updatedDate;
    
    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProcessStep> processSteps;
    
}
