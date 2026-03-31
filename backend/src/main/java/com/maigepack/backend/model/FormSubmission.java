package com.maigepack.backend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "form_submission")
public class FormSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String type;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 256)
    private String email;

    @Column(length = 64)
    private String phone;

    @Column(length = 128)
    private String company;

    @Column(length = 64)
    private String productType;

    @Column(length = 128)
    private String quantity;

    @Column(nullable = false)
    private String message;

    @Column(length = 64)
    private String industry;

    @Column(length = 128)
    private String timeline;

    @Column(length = 256)
    private String materialPreferences;

    @Column(length = 256)
    private String finishPreferences;

    @Column(length = 256)
    private String sizeRequirements;

    @Column(length = 16)
    private String status = "pending"; // pending, reviewed, quoted, completed

    @Column(length = 256)
    private String assignedTo;

    @Column
    private String adminNotes;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant reviewedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getMaterialPreferences() {
        return materialPreferences;
    }

    public void setMaterialPreferences(String materialPreferences) {
        this.materialPreferences = materialPreferences;
    }

    public String getFinishPreferences() {
        return finishPreferences;
    }

    public void setFinishPreferences(String finishPreferences) {
        this.finishPreferences = finishPreferences;
    }

    public String getSizeRequirements() {
        return sizeRequirements;
    }

    public void setSizeRequirements(String sizeRequirements) {
        this.sizeRequirements = sizeRequirements;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}

