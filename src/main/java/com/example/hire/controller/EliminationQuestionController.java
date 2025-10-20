package com.example.hire.controller;

import com.example.hire.entity.EliminationQuestion;
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
        return ResponseEntity.ok(eliminationQuestionService.createQuestion(question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        eliminationQuestionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}


