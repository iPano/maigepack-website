package com.maigepack.backend.api.forms;

import com.maigepack.backend.model.FormSubmission;
import com.maigepack.backend.repository.FormSubmissionRepository;
import com.maigepack.backend.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {

    @Mock
    private FormSubmissionRepository submissionRepository;
    @Mock
    private EmailService emailService;

    private QuoteController controller;

    @BeforeEach
    void setUp() {
        controller = new QuoteController(submissionRepository, emailService);
    }

    // -----------------------------------------------------------------------
    // Response status
    // -----------------------------------------------------------------------

    @Test
    void submitQuote_validRequest_returns202Accepted() {
        ResponseEntity<Void> response = controller.submitQuote(buildRequest());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    // -----------------------------------------------------------------------
    // Repository interactions
    // -----------------------------------------------------------------------

    @Test
    void submitQuote_validRequest_savesSubmissionToRepository() {
        controller.submitQuote(buildRequest());
        verify(submissionRepository, times(1)).save(any(FormSubmission.class));
    }

    @Test
    void submitQuote_savedSubmission_hasStatusPending() {
        ArgumentCaptor<FormSubmission> captor = ArgumentCaptor.forClass(FormSubmission.class);

        controller.submitQuote(buildRequest());

        verify(submissionRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo("pending");
    }

    @Test
    void submitQuote_savedSubmission_mapsAllFieldsFromRequest() {
        QuoteRequest req = buildRequest();
        ArgumentCaptor<FormSubmission> captor = ArgumentCaptor.forClass(FormSubmission.class);

        controller.submitQuote(req);

        verify(submissionRepository).save(captor.capture());
        FormSubmission saved = captor.getValue();

        assertThat(saved.getType()).isEqualTo(req.getType());
        assertThat(saved.getName()).isEqualTo(req.getName());
        assertThat(saved.getEmail()).isEqualTo(req.getEmail());
        assertThat(saved.getPhone()).isEqualTo(req.getPhone());
        assertThat(saved.getCompany()).isEqualTo(req.getCompany());
        assertThat(saved.getProductType()).isEqualTo(req.getProductType());
        assertThat(saved.getQuantity()).isEqualTo(req.getQuantity());
        assertThat(saved.getMessage()).isEqualTo(req.getMessage());
        assertThat(saved.getIndustry()).isEqualTo(req.getIndustry());
        assertThat(saved.getTimeline()).isEqualTo(req.getTimeline());
        assertThat(saved.getMaterialPreferences()).isEqualTo(req.getMaterialPreferences());
        assertThat(saved.getFinishPreferences()).isEqualTo(req.getFinishPreferences());
        assertThat(saved.getSizeRequirements()).isEqualTo(req.getSizeRequirements());
    }

    // -----------------------------------------------------------------------
    // Email service interactions
    // -----------------------------------------------------------------------

    @Test
    void submitQuote_validRequest_sendsEmailNotification() {
        controller.submitQuote(buildRequest());
        verify(emailService, times(1)).sendQuoteNotification(any(FormSubmission.class));
    }

    @Test
    void submitQuote_emailServiceReceivesSavedSubmission() {
        ArgumentCaptor<FormSubmission> saveCaptor = ArgumentCaptor.forClass(FormSubmission.class);
        ArgumentCaptor<FormSubmission> emailCaptor = ArgumentCaptor.forClass(FormSubmission.class);

        controller.submitQuote(buildRequest());

        verify(submissionRepository).save(saveCaptor.capture());
        verify(emailService).sendQuoteNotification(emailCaptor.capture());

        // The same FormSubmission instance should be passed to both
        assertThat(saveCaptor.getValue()).isSameAs(emailCaptor.getValue());
    }

    @Test
    void submitQuote_saveBeforeEmail_orderIsPreserved() {
        var inOrder = inOrder(submissionRepository, emailService);

        controller.submitQuote(buildRequest());

        inOrder.verify(submissionRepository).save(any());
        inOrder.verify(emailService).sendQuoteNotification(any());
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private QuoteRequest buildRequest() {
        QuoteRequest req = new QuoteRequest();
        req.setType("quote");
        req.setName("Alice Smith");
        req.setEmail("alice@example.com");
        req.setPhone("555-1234");
        req.setCompany("Acme Ltd");
        req.setProductType("Packaging");
        req.setQuantity("500");
        req.setMessage("I need custom boxes");
        req.setIndustry("retail");
        req.setTimeline("3 weeks");
        req.setMaterialPreferences("cardboard");
        req.setFinishPreferences("matte");
        req.setSizeRequirements("30x20x10cm");
        return req;
    }
}
