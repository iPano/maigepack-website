package com.magerpack.backend.repository;

import com.magerpack.backend.model.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {
}

