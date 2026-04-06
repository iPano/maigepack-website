package com.magerpack.backend.service;

import com.magerpack.backend.model.FormSubmission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String toEmail;

    public EmailService(JavaMailSender mailSender, @Value("${app.notification.toEmail:}") String toEmail) {
        this.mailSender = mailSender;
        this.toEmail = toEmail;
    }

    public void sendQuoteNotification(FormSubmission submission) {
        // Allow running dev without SMTP configured.
        if (toEmail == null || toEmail.isBlank()) {
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("New " + submission.getType() + " submission from " + submission.getName());

        String phone = submission.getPhone() == null ? "" : submission.getPhone();
        msg.setText(
                "Type: " + submission.getType() + "\n" +
                "Name: " + submission.getName() + "\n" +
                "Email: " + submission.getEmail() + "\n" +
                "Phone: " + phone + "\n\n" +
                "Message:\n" + submission.getMessage() + "\n"
        );
        msg.setFrom(toEmail);

        mailSender.send(msg);
    }
}

