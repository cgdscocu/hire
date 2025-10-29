package com.example.hire.controller;

import com.example.hire.dto.FormPublicResponseDTO;
import com.example.hire.dto.FormSubmitRequestDTO;
import com.example.hire.entity.ApplicationDetails;
import com.example.hire.entity.Process;
import com.example.hire.service.ApplicationFormService;
import com.example.hire.service.EvaluationService;
import com.example.hire.repository.ProcessRepository;
import com.example.hire.repository.EliminationQuestionRepository;
import com.example.hire.entity.EliminationQuestion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/forms")
public class PublicFormController {

    private final ApplicationFormService applicationFormService;
    private final ProcessRepository processRepository;
    private final EliminationQuestionRepository questionRepository;
    private final EvaluationService evaluationService;

    public PublicFormController(ApplicationFormService applicationFormService,
                                ProcessRepository processRepository,
                                EliminationQuestionRepository questionRepository,
                                EvaluationService evaluationService) {
        this.applicationFormService = applicationFormService;
        this.processRepository = processRepository;
        this.questionRepository = questionRepository;
        this.evaluationService = evaluationService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<FormPublicResponseDTO> getForm(@PathVariable String token) {
        ApplicationDetails details = applicationFormService.getByTokenOrThrow(token);
        Process process = details.getProcessStep().getProcess();

        FormPublicResponseDTO dto = new FormPublicResponseDTO();
        dto.setProjectId(process.getProject().getId());
        dto.setProjectName(process.getProject().getProjectName());
        dto.setProcess(process.getProcessType().name());
        dto.setExpiresAt(details.getFormExpiryDate());
        dto.setPersonalInfo(new FormPublicResponseDTO.PersonalInfoConfig(true, true, true, true));

        List<EliminationQuestion> questions =
            questionRepository.findByProjectIdAndProcessIdAndActiveTrueOrderByDisplayOrderAsc(
                process.getProject().getId(), process.getId());

        dto.setQuestions(questions.stream().map(q -> new FormPublicResponseDTO.QuestionDTO(
            q.getId(), q.getQuestionText(), q.getAnswerType(), Boolean.TRUE.equals(q.getIsMustHave()),
            q.getOptions().stream().map(o -> new FormPublicResponseDTO.OptionDTO(o.getLabel(), o.getValue(), o.getRank())).collect(Collectors.toList())
        )).collect(Collectors.toList()));

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{token}/submit")
    public ResponseEntity<?> submit(@PathVariable String token, @RequestBody FormSubmitRequestDTO body) {
        ApplicationDetails details = applicationFormService.getByTokenOrThrow(token);
        Process process = details.getProcessStep().getProcess();

        applicationFormService.updatePersonalInfo(token,
            body.getPersonalInfo() != null ? body.getPersonalInfo().getName() : null,
            body.getPersonalInfo() != null ? body.getPersonalInfo().getEmail() : null,
            body.getPersonalInfo() != null ? body.getPersonalInfo().getPhone() : null,
            body.getPersonalInfo() != null ? body.getPersonalInfo().getCvUrl() : null);

        boolean eliminated = evaluationService.isEliminatedByMustHave(
            process.getProject().getId(), process.getId(), body.getAnswers());

        details.setIsEliminated(eliminated);
        applicationFormService.getByTokenOrThrow(token); // ensure exists

        return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
            put("accepted", !eliminated);
            put("eliminated", eliminated);
            put("message", eliminated ? "Başvuru elendi" : "Başvurunuz alındı");
        }});
    }
}


