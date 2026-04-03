# Feature Specification: Product Detail Page

**Feature Branch**: `001-product-detail-page`
**Created**: 2026-04-03
**Status**: Draft

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Browse Full Product Information (Priority: P1)

A prospective B2B buyer lands on a product detail page (either from the catalogue or a direct
link/search engine). They need to see the complete picture of a product — gallery, description,
key features, technical specifications, materials, size range, minimum order quantity, and lead
time — so they can decide whether the product fits their packaging needs without having to
contact sales first.

**Why this priority**: This is the core purpose of the page. Without complete product information,
every other user story has no foundation. It is the primary conversion page between browsing and
quoting.

**Independent Test**: Open any product's URL (`/products/{slug}`) directly. Verify the page
displays the product name, at least one image, a description, and the key specification block
(MOQ, lead time, materials, size range) without requiring any login or prior navigation.

**Acceptance Scenarios**:

1. **Given** a visitor arrives at `/products/rigid-boxes`, **When** the page loads, **Then** they see the product name, main image, short description, category badge, material options, size range, minimum order quantity, lead time, key features list, and full technical specifications.
2. **Given** a product has multiple images, **When** the visitor views the page, **Then** they can browse all additional images via a thumbnail gallery below the main image, with the main image updating when a thumbnail is clicked.
3. **Given** a product has no additional images, **When** the visitor views the page, **Then** only the main image is shown with no broken or empty gallery UI.
4. **Given** a product exists but has no value for an optional field (e.g., `finishOptions` is empty), **When** the page renders, **Then** that field is gracefully omitted rather than showing an empty label.
5. **Given** a visitor arrives at `/products/non-existent-slug`, **When** the page loads, **Then** they see a clear "Product Not Found" message and a link back to the product catalogue.

---

### User Story 2 - Request a Quote from the Product Page (Priority: P2)

After reviewing a product, the visitor wants to request a quote. They click "Get Free Quote"
and the contact form opens pre-filled with the product name and type, so they don't have to
manually re-enter what they were looking at.

**Why this priority**: The quote request is the primary business conversion event. Pre-filling
the form with the product context dramatically reduces friction and abandonment at this critical
step.

**Independent Test**: On any product detail page, click "Get Free Quote". Verify the contact/
quote form is pre-filled with the product name referenced in the message and the correct product
type selected in the dropdown — without any extra steps from the visitor.

**Acceptance Scenarios**:

1. **Given** a visitor is on the Rigid Boxes product page, **When** they click "Get Free Quote", **Then** they arrive at the contact form with `productType` pre-selected to match the product's category and the `message` field referencing the product name.
2. **Given** a visitor clicks "Get Free Quote" on a product whose category does not match any dropdown option, **When** the form opens, **Then** `productType` defaults to "other" and the product name is still referenced in the message.
3. **Given** the visitor edits the pre-filled form fields, **When** they submit, **Then** their edited values — not the original pre-filled ones — are submitted.

---

### User Story 3 - Discover Related Products (Priority: P3)

After viewing a product, the visitor sees a "Related Products" section showing other products
in the same category. This keeps them engaged and helps them find alternatives or complementary
packaging solutions.

**Why this priority**: Increases time on site and surfaces products the visitor may not have
found through search. Valuable but not blocking for the core experience.

**Independent Test**: Navigate to any product detail page. Scroll to the bottom. Verify that
a "Related Products" section appears with at least one other product card from the same
category (when one exists), and that each card links to its own detail page.

**Acceptance Scenarios**:

1. **Given** a product belongs to a category with other active products, **When** the visitor views the detail page, **Then** up to 4 related products from the same category are shown, excluding the current product.
2. **Given** a product is the only product in its category, **When** the visitor views the page, **Then** the related products section is hidden rather than showing an empty container.
3. **Given** a visitor clicks a related product card, **When** the navigation completes, **Then** they land on that related product's own detail page.

---

### User Story 4 - SEO-Optimised Page for Search Engines (Priority: P4)

Each product detail page has a unique, descriptive page title and meta description so that
search engines can index and rank the page for relevant packaging queries.

**Why this priority**: Organic search is a key acquisition channel for B2B packaging buyers.
Poor SEO on product pages means invisible products.

**Independent Test**: View the page source or use browser devtools to inspect `<title>` and
`<meta name="description">` for any product page. Verify that both contain the product name
and are unique to that product (not generic site-wide values).

**Acceptance Scenarios**:

1. **Given** any product detail page is loaded, **When** the page head is inspected, **Then** `<title>` includes the product name and brand name (e.g., "Rigid Boxes | MaigePack") and `<meta name="description">` contains the product's short description.
2. **Given** two different product pages are loaded, **When** their `<title>` and `<meta name="description">` are compared, **Then** both values differ between the two pages.

---

### Edge Cases

- What happens when the product `description` is very long (5,000+ characters)? Layout must not break; content must be scrollable.
- What happens when `additionalImages` contains a broken image URL? A placeholder image MUST be shown instead of a broken icon.
- What happens when `features` or `specifications` are empty? Those sections must be hidden, not rendered as empty headings.
- What happens when the API is unreachable? The page must show a friendly error state rather than a blank white screen.
- What happens when a visitor deep-links directly to a product URL on a fresh browser session? The page must load correctly without prior navigation.
- What happens when the same product slug is accessed on mobile? All content must be accessible without horizontal scrolling.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The product detail page MUST display: name, category badge, main image, short description, full description, key features list, technical specifications table, material options, finish options, size range, minimum order quantity, lead time, and target industries — where data is present.
- **FR-002**: Optional fields (`finishOptions`, `targetIndustries`, `features`, `specifications`, `materialOptions`, `sizeRange`) MUST be omitted from the UI when their value is null, empty string, or empty collection.
- **FR-003**: When `additionalImages` is non-empty, the page MUST display a clickable thumbnail gallery; clicking a thumbnail MUST replace the main displayed image.
- **FR-004**: The "Get Free Quote" CTA MUST navigate to the contact form page with the product's name referenced in the message field and the matching product type pre-selected.
- **FR-005**: The page MUST display up to 4 related products from the same category, excluding the current product.
- **FR-006**: The related products section MUST be hidden when no other active products exist in the same category.
- **FR-007**: The page MUST render a "Product Not Found" state — with a link back to the catalogue — when the product slug does not match any active product.
- **FR-008**: Every product detail page MUST have a unique `<title>` tag containing the product name and brand, and a `<meta name="description">` containing the product's short description.
- **FR-009**: The page MUST include a breadcrumb trail: Home → Products → [Product Name], with Home and Products as navigable links.
- **FR-010**: Broken image URLs (main or thumbnail) MUST fall back to a placeholder image without breaking the page layout.
- **FR-011**: The page MUST be fully functional and readable on screen widths from 320px upward, with no horizontal overflow.

### Key Entities

- **Product**: The central entity displayed on the page. Relevant attributes: `name`, `slug`, `category`, `shortDescription`, `description`, `imageUrl`, `additionalImages`, `features`, `specifications`, `materialOptions`, `finishOptions`, `sizeRange`, `minimumOrderQuantity`, `leadTime`, `targetIndustries`.
- **Related Product**: A summary view of another product in the same category, showing: `name`, `slug`, `imageUrl`, `shortDescription`, `category`.
- **Quote Pre-fill**: A derived payload combining a mapped `productType` value (from the product's `category`) and a pre-composed message referencing the product name, passed to the contact form.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: A visitor can find and read all key product information (images, specs, features, CTA) on a single page without navigating away to gather basic details.
- **SC-002**: The product detail page displays content in under 2 seconds on a standard broadband connection.
- **SC-003**: Clicking "Get Free Quote" from any product page results in the contact form arriving with correct product pre-fill 100% of the time.
- **SC-004**: 100% of active product pages have a unique, non-empty `<title>` and `<meta name="description">`.
- **SC-005**: The "Product Not Found" state renders correctly for any invalid slug — verified across multiple invalid slugs with zero blank screens or unhandled error pages.
- **SC-006**: The page renders without horizontal scrolling or layout breakage at 320px, 768px, and 1280px viewport widths.

## Assumptions

- The existing `/api/public/products/{slug}` backend endpoint already returns all required fields and does not need modification for this feature.
- The existing products-by-category endpoint is sufficient to fetch related products; no new backend endpoint is required.
- The contact form at `/contact-us` accepts pre-fill values via URL query parameters or router state; the exact mechanism is a planning-phase decision.
- No user authentication is required to view the product detail page.
- No pricing information is displayed — all pricing is handled via the quote request flow.
- The product `category` field can be mapped to one of the existing `productType` dropdown values; an "other" fallback handles unmapped categories.
- Only products with `active: true` are accessible; inactive product slugs return the "Not Found" state.
- The product detail page (`/products/[slug].vue`) already exists as a partial implementation; this spec defines the complete, correct behaviour it must deliver.
