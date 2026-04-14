package com.magerpack.backend.api.admin;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String slug;

    private String shortDescription;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    private String imageUrl;
    private List<String> additionalImages;
    private List<String> features;
    private Map<String, String> specifications;
    private List<String> targetIndustries;
    private String materialOptions;
    private String finishOptions;
    private String sizeRange;
    private Integer minimumOrderQuantity;
    private String leadTime;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private Boolean active = true;
    private Integer displayOrder = 0;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getAdditionalImages() { return additionalImages; }
    public void setAdditionalImages(List<String> additionalImages) { this.additionalImages = additionalImages; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }

    public Map<String, String> getSpecifications() { return specifications; }
    public void setSpecifications(Map<String, String> specifications) { this.specifications = specifications; }

    public List<String> getTargetIndustries() { return targetIndustries; }
    public void setTargetIndustries(List<String> targetIndustries) { this.targetIndustries = targetIndustries; }

    public String getMaterialOptions() { return materialOptions; }
    public void setMaterialOptions(String materialOptions) { this.materialOptions = materialOptions; }

    public String getFinishOptions() { return finishOptions; }
    public void setFinishOptions(String finishOptions) { this.finishOptions = finishOptions; }

    public String getSizeRange() { return sizeRange; }
    public void setSizeRange(String sizeRange) { this.sizeRange = sizeRange; }

    public Integer getMinimumOrderQuantity() { return minimumOrderQuantity; }
    public void setMinimumOrderQuantity(Integer minimumOrderQuantity) { this.minimumOrderQuantity = minimumOrderQuantity; }

    public String getLeadTime() { return leadTime; }
    public void setLeadTime(String leadTime) { this.leadTime = leadTime; }

    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }

    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }

    public String getMetaKeywords() { return metaKeywords; }
    public void setMetaKeywords(String metaKeywords) { this.metaKeywords = metaKeywords; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
