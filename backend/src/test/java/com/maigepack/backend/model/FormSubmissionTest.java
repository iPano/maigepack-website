package com.maigepack.backend.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class FormSubmissionTest {

    // -----------------------------------------------------------------------
    // prePersist
    // -----------------------------------------------------------------------

    @Test
    void prePersist_whenCreatedAtIsNull_setsCreatedAtToNow() {
        FormSubmission submission = new FormSubmission();
        assertThat(submission.getCreatedAt()).isNull();

        Instant before = Instant.now();
        submission.prePersist();
        Instant after = Instant.now();

        assertThat(submission.getCreatedAt())
                .isNotNull()
                .isAfterOrEqualTo(before)
                .isBeforeOrEqualTo(after);
    }

    @Test
    void prePersist_whenCreatedAtIsAlreadySet_doesNotOverwrite() {
        FormSubmission submission = new FormSubmission();
        // Simulate a submission that already has a createdAt (e.g. set by migration)
        // We must use reflection because there's no public setter for createdAt
        Instant fixed = Instant.parse("2024-01-01T10:00:00Z");
        try {
            var field = FormSubmission.class.getDeclaredField("createdAt");
            field.setAccessible(true);
            field.set(submission, fixed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        submission.prePersist();

        assertThat(submission.getCreatedAt()).isEqualTo(fixed);
    }

    @Test
    void prePersist_calledMultipleTimes_createdAtRemainsStable() {
        FormSubmission submission = new FormSubmission();
        submission.prePersist();
        Instant first = submission.getCreatedAt();

        // A brief pause to ensure clock would advance
        submission.prePersist();

        assertThat(submission.getCreatedAt()).isEqualTo(first);
    }

    // -----------------------------------------------------------------------
    // Default status
    // -----------------------------------------------------------------------

    @Test
    void newFormSubmission_hasDefaultStatusPending() {
        FormSubmission submission = new FormSubmission();
        assertThat(submission.getStatus()).isEqualTo("pending");
    }

    // -----------------------------------------------------------------------
    // Getters / setters for key fields
    // -----------------------------------------------------------------------

    @Test
    void settersAndGetters_roundTrip() {
        FormSubmission submission = new FormSubmission();
        submission.setType("quote");
        submission.setName("Alice");
        submission.setEmail("alice@example.com");
        submission.setPhone("555-0000");
        submission.setCompany("ACME");
        submission.setProductType("Box");
        submission.setQuantity("100");
        submission.setMessage("Need 100 boxes");
        submission.setIndustry("retail");
        submission.setTimeline("2 weeks");
        submission.setMaterialPreferences("cardboard");
        submission.setFinishPreferences("gloss");
        submission.setSizeRequirements("10x10x10cm");
        submission.setStatus("reviewed");
        submission.setAssignedTo("bob");
        submission.setAdminNotes("Follow up needed");
        Instant reviewed = Instant.now();
        submission.setReviewedAt(reviewed);

        assertThat(submission.getType()).isEqualTo("quote");
        assertThat(submission.getName()).isEqualTo("Alice");
        assertThat(submission.getEmail()).isEqualTo("alice@example.com");
        assertThat(submission.getPhone()).isEqualTo("555-0000");
        assertThat(submission.getCompany()).isEqualTo("ACME");
        assertThat(submission.getProductType()).isEqualTo("Box");
        assertThat(submission.getQuantity()).isEqualTo("100");
        assertThat(submission.getMessage()).isEqualTo("Need 100 boxes");
        assertThat(submission.getIndustry()).isEqualTo("retail");
        assertThat(submission.getTimeline()).isEqualTo("2 weeks");
        assertThat(submission.getMaterialPreferences()).isEqualTo("cardboard");
        assertThat(submission.getFinishPreferences()).isEqualTo("gloss");
        assertThat(submission.getSizeRequirements()).isEqualTo("10x10x10cm");
        assertThat(submission.getStatus()).isEqualTo("reviewed");
        assertThat(submission.getAssignedTo()).isEqualTo("bob");
        assertThat(submission.getAdminNotes()).isEqualTo("Follow up needed");
        assertThat(submission.getReviewedAt()).isEqualTo(reviewed);
    }
}
