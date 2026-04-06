package com.magerpack.backend.api.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuoteRequest {
    @NotBlank
    @Size(max = 32)
    private String type; // e.g. "quote" or "contact"

    @NotBlank
    @Size(max = 128)
    private String name;

    @NotBlank
    @Email
    @Size(max = 256)
    private String email;

    @Size(max = 64)
    private String phone;

    @Size(max = 128)
    private String company;

    @Size(max = 64)
    private String productType;

    @Size(max = 128)
    private String quantity;

    @NotBlank
    private String message;

    @Size(max = 64)
    private String industry;

    @Size(max = 128)
    private String timeline;

    @Size(max = 256)
    private String materialPreferences;

    @Size(max = 256)
    private String finishPreferences;

    @Size(max = 256)
    private String sizeRequirements;

    // Getters and Setters
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
}

