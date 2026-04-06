package com.magerpack.backend.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "industry")
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 256)
    private String slug; // URL-friendly name (e.g., "electronics-packaging")

    @Column(nullable = false)
    private String description;

    @Column(length = 1024)
    private String shortDescription;

    @Column(length = 512)
    private String heroImageUrl;

    @Column(length = 512)
    private String iconUrl;

    @ElementCollection
    @CollectionTable(name = "industry_key_benefits", joinColumns = @JoinColumn(name = "industry_id"))
    @Column(name = "benefit", length = 256)
    private List<String> keyBenefits;

    @ElementCollection
    @CollectionTable(name = "industry_challenges", joinColumns = @JoinColumn(name = "industry_id"))
    @Column(name = "challenge", length = 256)
    private List<String> commonChallenges;

    @ElementCollection
    @CollectionTable(name = "industry_solutions", joinColumns = @JoinColumn(name = "industry_id"))
    @Column(name = "solution", length = 512)
    private List<String> packagingSolutions;

    @ElementCollection
    @CollectionTable(name = "industry_product_types", joinColumns = @JoinColumn(name = "industry_id"))
    @Column(name = "product_type", length = 64)
    private List<String> recommendedProductTypes;

    @ElementCollection
    @CollectionTable(name = "industry_case_studies", joinColumns = @JoinColumn(name = "industry_id"))
    @Column(name = "case_study", length = 1024)
    private List<String> caseStudies;

    @Column(length = 256)
    private String metaTitle;

    @Column(length = 512)
    private String metaDescription;

    @ElementCollection
    @CollectionTable(name = "industry_keywords", joinColumns = @JoinColumn(name = "industry_id"))
    @Column(name = "keyword", length = 64)
    private List<String> seoKeywords;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<String> getKeyBenefits() {
        return keyBenefits;
    }

    public void setKeyBenefits(List<String> keyBenefits) {
        this.keyBenefits = keyBenefits;
    }

    public List<String> getCommonChallenges() {
        return commonChallenges;
    }

    public void setCommonChallenges(List<String> commonChallenges) {
        this.commonChallenges = commonChallenges;
    }

    public List<String> getPackagingSolutions() {
        return packagingSolutions;
    }

    public void setPackagingSolutions(List<String> packagingSolutions) {
        this.packagingSolutions = packagingSolutions;
    }

    public List<String> getRecommendedProductTypes() {
        return recommendedProductTypes;
    }

    public void setRecommendedProductTypes(List<String> recommendedProductTypes) {
        this.recommendedProductTypes = recommendedProductTypes;
    }

    public List<String> getCaseStudies() {
        return caseStudies;
    }

    public void setCaseStudies(List<String> caseStudies) {
        this.caseStudies = caseStudies;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public List<String> getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(List<String> seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}