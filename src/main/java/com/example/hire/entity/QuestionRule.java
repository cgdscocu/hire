package com.example.hire.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private EliminationQuestion question;

    @Column(name = "rule_code", nullable = false, length = 100)
    private String ruleCode;

    @Column(name = "operator", nullable = false, length = 10)
    private String operator; // e.g. ==, !=, >, >=, <, <=, IN, NOT_IN

    @Column(name = "target_value", nullable = false, length = 500)
    private String targetValue;
}


