package com.maigepack.backend.repository;

import com.maigepack.backend.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    // Find by slug for URL-friendly access
    Optional<Industry> findBySlug(String slug);

    // Find active industries
    List<Industry> findByActiveOrderByDisplayOrder(Boolean active);

    // Search industries by name or description
    @Query("SELECT i FROM Industry i WHERE i.active = true AND " +
           "(LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(i.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(i.shortDescription) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Industry> searchIndustries(@Param("query") String query);

    // Get featured industries (first N by display order)
    @Query("SELECT i FROM Industry i WHERE i.active = true ORDER BY i.displayOrder")
    List<Industry> findFeaturedIndustries();
}