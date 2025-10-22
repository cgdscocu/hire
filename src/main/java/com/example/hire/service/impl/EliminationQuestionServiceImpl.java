package com.example.hire.service.impl;

import com.example.hire.entity.EliminationQuestion;
import com.example.hire.entity.Process;
import com.example.hire.entity.Project;
import com.example.hire.entity.QuestionOption;
import com.example.hire.entity.QuestionRule;
import com.example.hire.exception.NotFoundException;
import com.example.hire.repository.EliminationQuestionRepository;
import com.example.hire.repository.ProcessRepository;
import com.example.hire.repository.ProjectRepository;
import com.example.hire.service.EliminationQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EliminationQuestionServiceImpl implements EliminationQuestionService {

    private final EliminationQuestionRepository eliminationQuestionRepository;
    private final ProjectRepository projectRepository;
    private final ProcessRepository processRepository;

    public EliminationQuestionServiceImpl(EliminationQuestionRepository eliminationQuestionRepository,
                                        ProjectRepository projectRepository,
                                        ProcessRepository processRepository) {
        this.eliminationQuestionRepository = eliminationQuestionRepository;
        this.projectRepository = projectRepository;
        this.processRepository = processRepository;
    }

    @Override
    public List<EliminationQuestion> getActiveQuestions() {
        return eliminationQuestionRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    public EliminationQuestion createQuestion(EliminationQuestion question) {
        // Project ve Process entity'lerini set et
        if (question.getProject() != null && question.getProject().getId() != null) {
            Project project = projectRepository.findById(question.getProject().getId())
                .orElseThrow(() -> new NotFoundException("Project not found with ID: " + question.getProject().getId()));
            question.setProject(project);
        }
        
        if (question.getProcess() != null && question.getProcess().getId() != null) {
            Process process = processRepository.findById(question.getProcess().getId())
                .orElseThrow(() -> new NotFoundException("Process not found with ID: " + question.getProcess().getId()));
            question.setProcess(process);
        }
        
        if (question.getOptions() != null) {
            for (QuestionOption opt : question.getOptions()) {
                opt.setQuestion(question);
            }
        }
        if (question.getRules() != null) {
            for (QuestionRule rule : question.getRules()) {
                rule.setQuestion(question);
            }
        }
        return eliminationQuestionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        eliminationQuestionRepository.deleteById(id);
    }
}


