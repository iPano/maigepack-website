package com.magerpack.backend.service;

import com.magerpack.backend.model.FormSubmission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    // -----------------------------------------------------------------------
    // No-op paths — when toEmail is absent
    // -----------------------------------------------------------------------

    @Test
    void sendQuoteNotification_whenToEmailIsBlank_doesNotSendMail() {
        EmailService service = new EmailService(mailSender, "");
        service.sendQuoteNotification(buildSubmission("quote", "Alice", "alice@example.com", "555-1234"));
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendQuoteNotification_whenToEmailIsNull_doesNotSendMail() {
        EmailService service = new EmailService(mailSender, null);
        service.sendQuoteNotification(buildSubmission("contact", "Bob", "bob@example.com", null));
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendQuoteNotification_whenToEmailIsWhitespace_doesNotSendMail() {
        EmailService service = new EmailService(mailSender, "   ");
        service.sendQuoteNotification(buildSubmission("quote", "Carol", "carol@example.com", null));
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    // -----------------------------------------------------------------------
    // Email is sent — verify recipients, subject, and body
    // -----------------------------------------------------------------------

    @Test
    void sendQuoteNotification_whenToEmailIsSet_sendsEmail() {
        EmailService service = new EmailService(mailSender, "notify@magerpack.com");
        service.sendQuoteNotification(buildSubmission("quote", "Alice", "alice@example.com", "555-1234"));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendQuoteNotification_sentMessage_hasCorrectRecipientAndFrom() {
        EmailService service = new EmailService(mailSender, "notify@magerpack.com");
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.sendQuoteNotification(buildSubmission("quote", "Alice", "alice@example.com", "555-1234"));

        verify(mailSender).send(captor.capture());
        SimpleMailMessage msg = captor.getValue();
        assertThat(msg.getTo()).containsExactly("notify@magerpack.com");
        assertThat(msg.getFrom()).isEqualTo("notify@magerpack.com");
    }

    @Test
    void sendQuoteNotification_sentMessage_subjectContainsTypeAndName() {
        EmailService service = new EmailService(mailSender, "notify@magerpack.com");
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.sendQuoteNotification(buildSubmission("quote", "Alice", "alice@example.com", null));

        verify(mailSender).send(captor.capture());
        String subject = captor.getValue().getSubject();
        assertThat(subject).contains("quote").contains("Alice");
    }

    @Test
    void sendQuoteNotification_sentMessage_bodyContainsAllFields() {
        EmailService service = new EmailService(mailSender, "notify@magerpack.com");
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        FormSubmission sub = buildSubmission("contact", "Bob", "bob@example.com", "555-9999");
        sub.setMessage("I need 500 boxes");
        service.sendQuoteNotification(sub);

        verify(mailSender).send(captor.capture());
        String text = captor.getValue().getText();
        assertThat(text)
                .contains("contact")
                .contains("Bob")
                .contains("bob@example.com")
                .contains("555-9999")
                .contains("I need 500 boxes");
    }

    @Test
    void sendQuoteNotification_nullPhone_bodyShowsEmptyPhoneLine() {
        EmailService service = new EmailService(mailSender, "notify@magerpack.com");
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.sendQuoteNotification(buildSubmission("quote", "Dave", "dave@example.com", null));

        verify(mailSender).send(captor.capture());
        // The body should still contain "Phone:" without throwing NPE
        assertThat(captor.getValue().getText()).contains("Phone:");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private FormSubmission buildSubmission(String type, String name, String email, String phone) {
        FormSubmission s = new FormSubmission();
        s.setType(type);
        s.setName(name);
        s.setEmail(email);
        s.setPhone(phone);
        s.setMessage("default message");
        return s;
    }
}
