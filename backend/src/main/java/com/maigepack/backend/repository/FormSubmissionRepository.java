package com.maigepack.backend.repository;

import com.maigepack.backend.model.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {
}

