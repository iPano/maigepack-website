package com.maigepack.backend.api.pub;

import com.maigepack.backend.model.Industry;
import com.maigepack.backend.repository.IndustryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/public/industries")
public class IndustryController {

    private final IndustryRepository industryRepository;

    public IndustryController(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @GetMapping
    public ResponseEntity<List<Industry>> getAllIndustries() {
        List<Industry> industries = industryRepository.findByActiveOrderByDisplayOrder(true);
        return ResponseEntity.ok(industries);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Industry> getIndustryBySlug(@PathVariable String slug) {
        Optional<Industry> industry = industryRepository.findBySlug(slug);
        return industry.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Industry>> getFeaturedIndustries(@RequestParam(defaultValue = "4") int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("displayOrder"));
        List<Industry> industries = industryRepository.findFeaturedIndustries();

        // Limit the results to the requested size
        List<Industry> limitedIndustries = industries.stream()
                .limit(limit)
                .toList();

        return ResponseEntity.ok(limitedIndustries);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Industry>> searchIndustries(@RequestParam String q) {
        List<Industry> industries = industryRepository.searchIndustries(q);
        return ResponseEntity.ok(industries);
    }
}