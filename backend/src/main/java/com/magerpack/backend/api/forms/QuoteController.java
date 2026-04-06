package com.magerpack.backend.api.forms;

import com.magerpack.backend.model.FormSubmission;
import com.magerpack.backend.repository.FormSubmissionRepository;
import com.magerpack.backend.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/forms")
public class QuoteController {
    private final FormSubmissionRepository submissionRepository;
    private final EmailService emailService;

    public QuoteController(FormSubmissionRepository submissionRepository, EmailService emailService) {
        this.submissionRepository = submissionRepository;
        this.emailService = emailService;
    }

    @PostMapping("/quote")
    public ResponseEntity<Void> submitQuote(@RequestBody @Valid QuoteRequest req) {
        FormSubmission submission = new FormSubmission();
        submission.setType(req.getType());
        submission.setName(req.getName());
        submission.setEmail(req.getEmail());
        submission.setPhone(req.getPhone());
        submission.setCompany(req.getCompany());
        submission.setProductType(req.getProductType());
        submission.setQuantity(req.getQuantity());
        submission.setMessage(req.getMessage());
        submission.setIndustry(req.getIndustry());
        submission.setTimeline(req.getTimeline());
        submission.setMaterialPreferences(req.getMaterialPreferences());
        submission.setFinishPreferences(req.getFinishPreferences());
        submission.setSizeRequirements(req.getSizeRequirements());
        submission.setStatus("pending");

        submissionRepository.save(submission);
        emailService.sendQuoteNotification(submission);

        return ResponseEntity.accepted().build();
    }
}

