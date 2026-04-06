package com.maigepack.backend.api.admin;

import java.time.Instant;

public class ProductSummaryResponse {

    private Long id;
    private String name;
    private String slug;
    private String category;
    private Boolean active;
    private Integer displayOrder;
    private Instant createdAt;

    public ProductSummaryResponse(Long id, String name, String slug, String category,
                                   Boolean active, Integer displayOrder, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.category = category;
        this.active = active;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getCategory() { return category; }
    public Boolean getActive() { return active; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Instant getCreatedAt() { return createdAt; }
}
