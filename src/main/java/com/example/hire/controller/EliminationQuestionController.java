package com.example.hire.controller;

import com.example.hire.entity.EliminationQuestion;
import com.example.hire.entity.Process;
import com.example.hire.entity.Project;
import com.example.hire.service.EliminationQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elimination-questions")
@CrossOrigin(origins = "*")
public class EliminationQuestionController {

    private final EliminationQuestionService eliminationQuestionService;

    public EliminationQuestionController(EliminationQuestionService eliminationQuestionService) {
        this.eliminationQuestionService = eliminationQuestionService;
    }

    @GetMapping
    public ResponseEntity<List<EliminationQuestion>> getActiveQuestions() {
        return ResponseEntity.ok(eliminationQuestionService.getActiveQuestions());
    }

    @PostMapping
    public ResponseEntity<EliminationQuestion> createQuestion(@RequestBody EliminationQuestion question) {
        if (question.getProject() == null && question.getProjectId() != null) {
            Project project = new Project();
            project.setId(question.getProjectId());
            question.setProject(project);
        }
        
        if (question.getProcess() == null && question.getProcessId() != null) {
            Process process = new Process();
            process.setId(question.getProcessId());
            question.setProcess(process);
        }
        
        return ResponseEntity.ok(eliminationQuestionService.createQuestion(question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        eliminationQuestionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}


