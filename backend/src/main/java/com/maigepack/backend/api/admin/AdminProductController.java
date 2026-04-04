package com.maigepack.backend.api.admin;

import com.maigepack.backend.model.Product;
import com.maigepack.backend.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
        "displayOrder", "name", "category", "createdAt", "active"
    );

    private final ProductRepository productRepository;

    public AdminProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET /api/admin/products — paginated list, all products (including inactive)
    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "displayOrder") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "displayOrder";
        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(safeSortBy).descending()
                : Sort.by(safeSortBy).ascending();
        Pageable pageable = PageRequest.of(page, Math.min(size, 100), sort);

        String queryParam = (search != null && !search.isBlank()) ? search.trim() : null;
        String categoryParam = (category != null && !category.isBlank()) ? category.trim() : null;

        Page<Product> products = productRepository.findAllProductsWithFilters(categoryParam, queryParam, pageable);
        Page<ProductSummaryResponse> response = products.map(p ->
            new ProductSummaryResponse(p.getId(), p.getName(), p.getSlug(),
                    p.getCategory(), p.getActive(), p.getDisplayOrder(), p.getCreatedAt())
        );

        return ResponseEntity.ok(response);
    }

    // GET /api/admin/products/{id} — single product for editing
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/admin/products — create new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = new Product();
        mapRequestToProduct(request, product);

        // Auto-generate slug if not provided
        String baseSlug = generateSlug(request.getSlug() != null && !request.getSlug().isBlank()
                ? request.getSlug() : request.getName());
        product.setSlug(uniqueSlug(baseSlug, null));

        Product saved = productRepository.save(product);
        return ResponseEntity.created(URI.create("/api/admin/products/" + saved.getId())).body(saved);
    }

    // PUT /api/admin/products/{id} — update existing product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                  @Valid @RequestBody ProductRequest request) {
        return productRepository.findById(id).map(product -> {
            String newSlugBase = request.getSlug() != null && !request.getSlug().isBlank()
                    ? request.getSlug() : request.getName();
            String newSlug = generateSlug(newSlugBase);

            // Only regenerate unique slug if it changed
            if (!newSlug.equals(product.getSlug())) {
                product.setSlug(uniqueSlug(newSlug, product.getSlug()));
            }

            mapRequestToProduct(request, product);
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/admin/products/{id} — delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Helpers ----

    private void mapRequestToProduct(ProductRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setShortDescription(request.getShortDescription());
        product.setImageUrl(request.getImageUrl());
        product.setAdditionalImages(request.getAdditionalImages());
        product.setFeatures(request.getFeatures());
        product.setSpecifications(request.getSpecifications());
        product.setTargetIndustries(request.getTargetIndustries());
        product.setMaterialOptions(request.getMaterialOptions());
        product.setFinishOptions(request.getFinishOptions());
        product.setSizeRange(request.getSizeRange());
        product.setMinimumOrderQuantity(request.getMinimumOrderQuantity());
        product.setLeadTime(request.getLeadTime());
        product.setActive(request.getActive() != null ? request.getActive() : true);
        product.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
    }

    private String generateSlug(String input) {
        return input.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    private String uniqueSlug(String base, String existingSlug) {
        String candidate = base;
        int suffix = 2;
        while (true) {
            if (candidate.equals(existingSlug)) {
                return candidate; // unchanged
            }
            if (productRepository.findBySlug(candidate).isEmpty()) {
                return candidate;
            }
            candidate = base + "-" + suffix;
            suffix++;
        }
    }
}
