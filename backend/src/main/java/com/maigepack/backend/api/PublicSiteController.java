package com.maigepack.backend.api;

import com.maigepack.backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicSiteController {

    private final ProductRepository productRepository;

    public PublicSiteController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/site/nav")
    public Map<String, Object> nav() {
        // Get categories from database, fallback to static list if empty
        List<String> categories = productRepository.findDistinctCategories();

        if (categories.isEmpty()) {
            // Fallback static categories for development
            categories = List.of(
                "Rigid Boxes",
                "Folding Boxes",
                "Display Boxes",
                "Packaging Sleeves",
                "Custom Inserts"
            );
        }

        return Map.of("categories", categories);
    }
}

