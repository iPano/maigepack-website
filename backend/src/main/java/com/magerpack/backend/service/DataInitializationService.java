package com.magerpack.backend.service;

import com.magerpack.backend.model.Product;
import com.magerpack.backend.model.Industry;
import com.magerpack.backend.repository.ProductRepository;
import com.magerpack.backend.repository.IndustryRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataInitializationService {

    private final ProductRepository productRepository;
    private final IndustryRepository industryRepository;

    public DataInitializationService(ProductRepository productRepository, IndustryRepository industryRepository) {
        this.productRepository = productRepository;
        this.industryRepository = industryRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        if (productRepository.count() == 0) {
            initializeProducts();
        }
        if (industryRepository.count() == 0) {
            initializeIndustries();
        }
    }

    private void initializeProducts() {
        // Rigid Boxes
        Product rigidBox1 = createProduct(
            "Premium Rigid Gift Box",
            "premium-rigid-gift-box",
            "Rigid Boxes",
            "Luxury rigid boxes perfect for high-end gift packaging with magnetic closure and premium finishing.",
            "Premium magnetic closure gift boxes that create an unforgettable unboxing experience. Perfect for luxury brands, jewelry, electronics, and premium gifts.",
            Arrays.asList(
                "Magnetic closure for premium feel",
                "Soft-touch finish available",
                "Custom foil stamping and embossing",
                "Eco-friendly materials",
                "Structural integrity testing",
                "Luxury unboxing experience"
            ),
            createSpecifications(
                "Material", "Art paper + Grey board",
                "Thickness", "1.5mm - 3mm grey board",
                "Closure Type", "Magnetic",
                "Printing", "Offset printing, CMYK + Pantone",
                "Finishes", "Matt/Gloss lamination, UV coating, foil stamping",
                "Insert Options", "EVA foam, cardboard dividers"
            ),
            Arrays.asList("Electronics", "Cosmetics", "Jewelry", "Luxury Gifts"),
            "Art paper, Kraft paper, Specialty papers",
            "Matt/Gloss lamination, UV coating, Foil stamping, Embossing",
            "10cm x 10cm to 50cm x 40cm",
            500,
            "7-10 days",
            1
        );

        Product rigidBox2 = createProduct(
            "Electronics Packaging Box",
            "electronics-packaging-box",
            "Rigid Boxes",
            "Protective rigid boxes designed specifically for electronics with anti-static features and custom inserts.",
            "Professional electronics packaging with ESD protection and custom foam inserts to ensure product safety during shipping and display.",
            Arrays.asList(
                "Anti-static materials available",
                "Custom foam inserts included",
                "Drop-test certified construction",
                "Clean room compatible materials",
                "Barcode printing ready",
                "Stackable design for storage"
            ),
            createSpecifications(
                "Material", "Corrugated + Anti-static coating",
                "ESD Protection", "Yes, surface resistivity 10^6-10^9 Ω",
                "Insert Material", "Anti-static PE foam",
                "Testing", "Drop test, compression test",
                "Printing", "Digital printing, screen printing",
                "Certification", "ROHS compliant"
            ),
            Arrays.asList("Electronics", "Tech Hardware"),
            "Corrugated board, Anti-static materials",
            "Anti-static coating, Digital printing",
            "5cm x 5cm to 60cm x 40cm",
            300,
            "5-8 days",
            2
        );

        // Folding Boxes
        Product foldingBox1 = createProduct(
            "Custom Folding Carton",
            "custom-folding-carton",
            "Folding Boxes",
            "Versatile folding cartons suitable for a wide range of products with cost-effective production and customizable designs.",
            "Cost-effective folding cartons that provide excellent protection while maintaining brand visibility. Perfect for retail packaging and shipping.",
            Arrays.asList(
                "Easy assembly flat-pack design",
                "High-quality offset printing",
                "Window options available",
                "Tear strips for easy opening",
                "Tamper-evident features",
                "Recyclable materials"
            ),
            createSpecifications(
                "Material", "SBS paperboard, Kraft paperboard",
                "Caliper", "12pt - 24pt",
                "Printing", "Flexographic, Offset printing",
                "Die-cutting", "Precision rotary die-cutting",
                "Gluing", "Straight-line gluing",
                "Special Features", "Windows, perforations, scores"
            ),
            Arrays.asList("Food & Beverage", "Cosmetics", "Pharmaceuticals", "Consumer Goods"),
            "SBS paperboard, Kraft paperboard, Recycled content",
            "Varnish, Lamination, Window patching",
            "5cm x 5cm to 40cm x 30cm",
            1000,
            "3-5 days",
            3
        );

        Product foldingBox2 = createProduct(
            "Pharmaceutical Folding Box",
            "pharmaceutical-folding-box",
            "Folding Boxes",
            "FDA-compliant folding boxes for pharmaceutical products with tamper-evident features and child-resistant options.",
            "Specialized pharmaceutical packaging meeting FDA regulations with tamper-evident seals and child-resistant features for medication safety.",
            Arrays.asList(
                "FDA compliant materials",
                "Tamper-evident sealing",
                "Child-resistant options",
                "Serialization ready",
                "Moisture barrier coating",
                "Braille text available"
            ),
            createSpecifications(
                "Material", "Pharmaceutical grade SBS",
                "Compliance", "FDA 21 CFR 176.170",
                "Barrier Properties", "Moisture, light, oxygen",
                "Tamper Evidence", "Tear tape, perforations",
                "Child Resistance", "CPSC certified options",
                "Serialization", "GS1 DataMatrix compatible"
            ),
            Arrays.asList("Pharmaceuticals", "Healthcare"),
            "Pharmaceutical grade paperboard",
            "Barrier coatings, Security printing",
            "8cm x 5cm to 25cm x 15cm",
            500,
            "7-12 days",
            4
        );

        // Display Boxes
        Product displayBox1 = createProduct(
            "Retail Display Stand",
            "retail-display-stand",
            "Display Boxes",
            "Eye-catching retail display stands designed to maximize product visibility and drive impulse purchases.",
            "Point-of-sale displays that combine functionality with visual appeal. Perfect for retail environments to showcase products effectively.",
            Arrays.asList(
                "Flat-pack for easy shipping",
                "Tool-free assembly",
                "High-impact graphics",
                "Multiple product slots",
                "Sturdy base design",
                "Promotional header space"
            ),
            createSpecifications(
                "Material", "Double wall corrugated",
                "Edge Crush", "5.5mm - 7mm thickness",
                "Graphics", "Litho laminated graphics",
                "Assembly", "Interlocking tabs",
                "Load Capacity", "Up to 20kg",
                "Dimensions", "Custom sizing available"
            ),
            Arrays.asList("Retail", "Consumer Goods", "Food & Beverage"),
            "Corrugated cardboard, Litho-laminated graphics",
            "UV coating, Spot UV, Embossing",
            "20cm x 30cm to 100cm x 150cm",
            100,
            "5-7 days",
            5
        );

        // Packaging Sleeves
        Product sleeve1 = createProduct(
            "Cosmetic Tube Sleeve",
            "cosmetic-tube-sleeve",
            "Packaging Sleeves",
            "Elegant packaging sleeves for cosmetic tubes and bottles that enhance brand presentation and protect products.",
            "Sophisticated sleeves that slide over cosmetic containers, providing additional branding space and premium feel for beauty products.",
            Arrays.asList(
                "Precise fit guarantee",
                "Soft-touch coating options",
                "Foil stamping capabilities",
                "Easy slide-on application",
                "Tear-resistant materials",
                "Sustainable material options"
            ),
            createSpecifications(
                "Material", "Art paper, Metallic paper",
                "Printing", "Offset printing, Digital printing",
                "Finishes", "Soft-touch, Foil stamping, Embossing",
                "Closure", "Overlap or tuck-in design",
                "Fit Tolerance", "+/- 0.5mm",
                "Special Features", "Window cutouts, perforations"
            ),
            Arrays.asList("Cosmetics", "Personal Care", "Luxury Goods"),
            "Art paper, Metallic substrates, Recycled content",
            "Soft-touch lamination, Foil stamping, Spot UV",
            "Tube diameter 15mm-80mm, Height 5cm-25cm",
            1000,
            "4-6 days",
            6
        );

        // Custom Inserts
        Product insert1 = createProduct(
            "Protective Foam Insert",
            "protective-foam-insert",
            "Custom Inserts",
            "Precision-cut foam inserts that provide superior protection for delicate products during shipping and storage.",
            "Custom-molded protective inserts designed to cradle your products securely, preventing damage during transit while creating a professional unboxing experience.",
            Arrays.asList(
                "CNC precision cutting",
                "Multiple density options",
                "Anti-static varieties",
                "Color customization",
                "Compression-resistant foam",
                "Eco-friendly alternatives"
            ),
            createSpecifications(
                "Material", "PE foam, PU foam, EVA foam",
                "Density", "20kg/m³ - 80kg/m³",
                "Cutting Method", "CNC, Water-jet, Die-cutting",
                "Colors", "Black, White, Custom colors",
                "Anti-Static", "Available for electronics",
                "Compression", "25% - 75% compression rating"
            ),
            Arrays.asList("Electronics", "Medical Devices", "Optics", "Industrial"),
            "PE foam, PU foam, EVA foam, Bio-based foams",
            "Anti-static treatment, Custom coloring",
            "Any custom size up to 200cm x 100cm",
            200,
            "3-5 days",
            7
        );

        List<Product> products = Arrays.asList(
            rigidBox1, rigidBox2, foldingBox1, foldingBox2, displayBox1, sleeve1, insert1
        );

        productRepository.saveAll(products);
        System.out.println("✅ Initialized " + products.size() + " sample products");
    }

    private void initializeIndustries() {
        // Electronics Industry
        Industry electronics = createIndustry(
            "Electronics Packaging",
            "electronics-packaging",
            "Specialized packaging solutions for electronic devices, components, and accessories with ESD protection and precision fit.",
            "Advanced packaging for electronics that ensures product safety during shipping while meeting industry standards for anti-static protection and handling.",
            Arrays.asList(
                "ESD (Electrostatic Discharge) protection for sensitive components",
                "Custom foam inserts for precise product positioning",
                "Anti-static materials and coatings",
                "Drop-test certified construction for shipping safety",
                "Clean room compatible materials and processes",
                "Barcode and QR code integration for inventory management"
            ),
            Arrays.asList(
                "Static electricity damage during handling and shipping",
                "Complex product shapes requiring custom inserts",
                "Need for product visibility while maintaining protection",
                "Regulatory compliance for electronic waste and materials",
                "High-volume production with consistent quality",
                "International shipping requirements and certifications"
            ),
            Arrays.asList(
                "Anti-static rigid boxes with custom foam inserts for perfect product fit",
                "Conductive and dissipative packaging materials to prevent ESD damage",
                "Clear anti-static windows for product visibility during retail display",
                "Modular insert systems for product line variations",
                "Tamper-evident sealing for high-value electronics",
                "Sustainable packaging options meeting ROHS compliance"
            ),
            Arrays.asList("Electronics Packaging Box", "Protective Foam Insert"),
            Arrays.asList(
                "Delivered 50,000 smartphone packaging units with zero damage claims during international shipping",
                "Developed custom laptop packaging reducing shipping damage by 95% for major tech brand",
                "Created modular tablet accessory packaging system saving 30% in packaging costs"
            ),
            "Electronics Packaging Solutions | Custom ESD Protection | MagerPack",
            "Professional electronics packaging with ESD protection, custom foam inserts, and anti-static materials. Perfect for smartphones, tablets, components, and accessories.",
            Arrays.asList("electronics packaging", "ESD protection", "anti-static boxes", "electronic component packaging", "smartphone packaging", "tablet packaging"),
            1
        );

        // Cosmetics Industry
        Industry cosmetics = createIndustry(
            "Cosmetics Packaging",
            "cosmetics-packaging",
            "Luxury packaging solutions for beauty and personal care products that enhance brand prestige and protect delicate formulations.",
            "Elegant cosmetics packaging that combines visual appeal with functional protection, creating an premium unboxing experience that reflects your brand's quality.",
            Arrays.asList(
                "Premium finishing options including soft-touch, foil stamping, and embossing",
                "Custom window designs for product visibility and appeal",
                "Specialized coatings to protect against moisture and UV damage",
                "Magnetic closures and luxury unboxing experiences",
                "Sustainable and eco-friendly material options",
                "Precise color matching and brand consistency across products"
            ),
            Arrays.asList(
                "Product protection from light, heat, and moisture damage",
                "Creating premium brand perception in competitive market",
                "Accommodating various product shapes and sizes in product lines",
                "Meeting cosmetic industry regulations and safety standards",
                "Seasonal packaging variations and limited edition requirements",
                "Balancing luxury appeal with cost-effective production"
            ),
            Arrays.asList(
                "Rigid luxury boxes with magnetic closures for premium skincare and makeup",
                "Custom tube sleeves with soft-touch finishes and foil stamping",
                "Window boxes with UV-protective coatings for product display",
                "Eco-friendly packaging options meeting sustainability goals",
                "Seasonal and limited edition packaging with special finishes",
                "Multi-product sets with custom inserts and compartments"
            ),
            Arrays.asList("Premium Rigid Gift Box", "Cosmetic Tube Sleeve"),
            Arrays.asList(
                "Launched luxury skincare line packaging that increased perceived value by 40%",
                "Created sustainable makeup packaging line reducing environmental impact by 60%",
                "Developed holiday gift set packaging that boosted seasonal sales by 25%"
            ),
            "Cosmetics Packaging Solutions | Luxury Beauty Packaging | MagerPack",
            "Premium cosmetics packaging with luxury finishes, custom designs, and brand-enhancing features. Perfect for skincare, makeup, fragrances, and personal care products.",
            Arrays.asList("cosmetics packaging", "luxury beauty packaging", "makeup packaging", "skincare packaging", "cosmetic boxes", "beauty product packaging"),
            2
        );

        // Jewelry Industry
        Industry jewelry = createIndustry(
            "Jewelry Packaging",
            "jewelry-packaging",
            "Exquisite packaging solutions for fine jewelry that provide security, elegance, and memorable presentation experiences.",
            "Sophisticated jewelry packaging that protects valuable pieces while creating an unforgettable moment of luxury and elegance for your customers.",
            Arrays.asList(
                "Velvet and satin lining options for delicate jewelry protection",
                "Security features including tamper-evident seals and locks",
                "Premium materials with metallic finishes and embossing",
                "Custom compartments for different jewelry types and sizes",
                "Compact designs optimized for high-value, small products",
                "Gift-ready presentation with luxury unboxing experience"
            ),
            Arrays.asList(
                "Protecting delicate and valuable items from scratches and damage",
                "Creating appropriate luxury perception for high-value products",
                "Accommodating various jewelry sizes from rings to necklaces",
                "Providing security during shipping and retail display",
                "Meeting insurance requirements for valuable item packaging",
                "Balancing compact size with adequate protection and presentation"
            ),
            Arrays.asList(
                "Luxury rigid boxes with velvet inserts and magnetic closures",
                "Security packaging with tamper-evident features and tracking",
                "Compact travel cases for jewelry protection during transport",
                "Gift presentation boxes with premium finishes and ribbons",
                "Display packaging for retail environments with security features",
                "Sustainable luxury options using recycled and eco-friendly materials"
            ),
            Arrays.asList("Premium Rigid Gift Box", "Custom Inserts"),
            Arrays.asList(
                "Created engagement ring packaging that enhanced proposal experience for luxury jeweler",
                "Developed security packaging reducing jewelry theft during shipping by 99%",
                "Designed modular jewelry collection boxes increasing customer retention by 35%"
            ),
            "Jewelry Packaging Solutions | Luxury Gift Boxes | MagerPack",
            "Exquisite jewelry packaging with velvet inserts, security features, and luxury finishes. Perfect for rings, necklaces, watches, and fine jewelry collections.",
            Arrays.asList("jewelry packaging", "luxury jewelry boxes", "engagement ring packaging", "jewelry gift boxes", "watch packaging", "fine jewelry packaging"),
            3
        );

        // Fragrance Industry
        Industry fragrance = createIndustry(
            "Fragrance Packaging",
            "fragrance-packaging",
            "Sophisticated packaging solutions for perfumes and fragrances that protect delicate scents while enhancing brand luxury.",
            "Elegant fragrance packaging that safeguards precious formulations while creating an sensory experience that reflects the sophistication of your scent.",
            Arrays.asList(
                "UV-protective materials to preserve fragrance integrity",
                "Shock-absorbing inserts for delicate glass bottles",
                "Luxury finishes including foil stamping and spot UV",
                "Custom bottle-shaped cutouts for perfect fit and presentation",
                "Premium unboxing experience enhancing the fragrance journey",
                "Seasonal and limited edition packaging capabilities"
            ),
            Arrays.asList(
                "Protecting glass bottles from breakage during shipping and handling",
                "Preserving fragrance quality from light and temperature exposure",
                "Creating luxury brand perception in competitive fragrance market",
                "Accommodating various bottle shapes and sizes in product lines",
                "Meeting international shipping regulations for fragrance products",
                "Seasonal marketing demands and gift packaging requirements"
            ),
            Arrays.asList(
                "Protective rigid boxes with custom foam inserts for fragrance bottles",
                "UV-blocking materials and coatings to preserve scent integrity",
                "Luxury gift sets with multiple product compartments",
                "Magnetic closure boxes with premium finishing and embossing",
                "Travel-size packaging with security and protection features",
                "Eco-luxury packaging options for sustainable fragrance brands"
            ),
            Arrays.asList("Premium Rigid Gift Box", "Protective Foam Insert"),
            Arrays.asList(
                "Developed signature fragrance packaging that became collector's item for luxury brand",
                "Created travel fragrance set reducing breakage claims to zero",
                "Designed holiday gift packaging that increased seasonal fragrance sales by 45%"
            ),
            "Fragrance Packaging Solutions | Luxury Perfume Boxes | MagerPack",
            "Sophisticated fragrance packaging with UV protection, custom inserts, and luxury finishes. Perfect for perfumes, colognes, and fragrance gift sets.",
            Arrays.asList("fragrance packaging", "perfume packaging", "luxury fragrance boxes", "perfume gift boxes", "cologne packaging", "fragrance gift sets"),
            4
        );

        List<Industry> industries = Arrays.asList(electronics, cosmetics, jewelry, fragrance);

        industryRepository.saveAll(industries);
        System.out.println("✅ Initialized " + industries.size() + " sample industries");
    }

    private Product createProduct(String name, String slug, String category, String shortDescription,
                                 String description, List<String> features, Map<String, String> specifications,
                                 List<String> targetIndustries, String materialOptions, String finishOptions,
                                 String sizeRange, Integer minOrderQuantity, String leadTime, Integer displayOrder) {
        Product product = new Product();
        product.setName(name);
        product.setSlug(slug);
        product.setCategory(category);
        product.setShortDescription(shortDescription);
        product.setDescription(description);
        product.setFeatures(features);
        product.setSpecifications(specifications);
        product.setTargetIndustries(targetIndustries);
        product.setMaterialOptions(materialOptions);
        product.setFinishOptions(finishOptions);
        product.setSizeRange(sizeRange);
        product.setMinimumOrderQuantity(minOrderQuantity);
        product.setLeadTime(leadTime);
        product.setActive(true);
        product.setDisplayOrder(displayOrder);
        return product;
    }

    private Industry createIndustry(String name, String slug, String shortDescription, String description,
                                   List<String> keyBenefits, List<String> commonChallenges,
                                   List<String> packagingSolutions, List<String> recommendedProductTypes,
                                   List<String> caseStudies, String metaTitle, String metaDescription,
                                   List<String> seoKeywords, Integer displayOrder) {
        Industry industry = new Industry();
        industry.setName(name);
        industry.setSlug(slug);
        industry.setShortDescription(shortDescription);
        industry.setDescription(description);
        industry.setKeyBenefits(keyBenefits);
        industry.setCommonChallenges(commonChallenges);
        industry.setPackagingSolutions(packagingSolutions);
        industry.setRecommendedProductTypes(recommendedProductTypes);
        industry.setCaseStudies(caseStudies);
        industry.setMetaTitle(metaTitle);
        industry.setMetaDescription(metaDescription);
        industry.setSeoKeywords(seoKeywords);
        industry.setActive(true);
        industry.setDisplayOrder(displayOrder);
        return industry;
    }

    private Map<String, String> createSpecifications(String... keyValuePairs) {
        Map<String, String> specs = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            specs.put(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return specs;
    }
}