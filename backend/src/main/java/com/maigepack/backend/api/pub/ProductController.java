package com.maigepack.backend.api.pub;

import com.maigepack.backend.model.Product;
import com.maigepack.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/public/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/simple")
    public ResponseEntity<List<Product>> getSimpleProducts() {
        try {
            List<Product> products = productRepository.findByActiveOrderByDisplayOrder(true);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.err.println("Error in simple products: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "displayOrder") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        try {
            // First try simple approach without custom query
            if (category == null && search == null) {
                Pageable pageable = PageRequest.of(page, size, Sort.by("displayOrder"));
                Page<Product> products = productRepository.findAll(pageable);
                return ResponseEntity.ok(products);
            }

            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

            // Validate sortBy field to prevent issues
            String safeSortBy = sortBy;
            if (!List.of("displayOrder", "name", "createdAt", "id").contains(sortBy)) {
                safeSortBy = "displayOrder";
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, safeSortBy));

            Page<Product> products = productRepository.findProductsWithFilters(
                    category != null ? category : "",
                    search != null ? search : "",
                    pageable);

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            // Log the error and return a fallback response
            System.err.println("Error fetching products: " + e.getMessage());
            e.printStackTrace();

            // Return empty page as fallback
            Page<Product> emptyPage = Page.empty(PageRequest.of(0, size));
            return ResponseEntity.ok(emptyPage);
        }
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Product> getProductBySlug(@PathVariable String slug) {
        Optional<Product> product = productRepository.findBySlug(slug);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = productRepository.findDistinctCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productRepository.findByCategoryAndActiveOrderByDisplayOrder(category, true);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String q) {
        List<Product> products = productRepository.searchProducts(q);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts(@RequestParam(defaultValue = "6") int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("displayOrder"));
        Page<Product> products = productRepository.findProductsWithFilters(null, null, pageable);
        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<Product>> getProductsByIndustry(@PathVariable String industry) {
        List<Product> products = productRepository.findByTargetIndustry(industry);
        return ResponseEntity.ok(products);
    }
}