package com.maigepack.backend.repository;

import com.maigepack.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find by slug for URL-friendly access
    Optional<Product> findBySlug(String slug);

    // Find active products
    List<Product> findByActiveOrderByDisplayOrder(Boolean active);

    // Find by category
    List<Product> findByCategoryAndActiveOrderByDisplayOrder(String category, Boolean active);

    // Search products by name or description
    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Product> searchProducts(@Param("query") String query);

    // Find products by target industry
    @Query("SELECT p FROM Product p JOIN p.targetIndustries i WHERE i = :industry AND p.active = true ORDER BY p.displayOrder")
    List<Product> findByTargetIndustry(@Param("industry") String industry);

    // Paginated search with category filter (simplified to avoid potential ElementCollection issues)
    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:query IS NULL OR " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Product> findProductsWithFilters(@Param("category") String category,
                                         @Param("query") String query,
                                         Pageable pageable);

    // Get distinct categories
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.active = true ORDER BY p.category")
    List<String> findDistinctCategories();
}