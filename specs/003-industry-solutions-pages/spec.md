# Feature Specification: Industry Solutions Pages

**Feature Branch**: `003-industry-solutions-pages`
**Created**: 2026-04-03
**Status**: Draft

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Browse All Industry Solutions (Priority: P1)

A B2B buyer who works in a specific industry (e.g., cosmetics, electronics, jewellery) lands on
the Industries overview page. They see all the sectors MaigePack serves, each with a brief
summary and key benefits, so they can quickly identify whether their industry is covered and
navigate to a dedicated page for their sector.

**Why this priority**: The industries overview is the entry point for the entire industry
solutions journey. Without it, buyers cannot discover or navigate to individual industry pages.

**Independent Test**: Navigate to `/industries`. Verify all active industries are listed, each
showing a name, short description, hero image, and a "Learn More" link. Clicking a card navigates
to the correct industry detail page.

**Acceptance Scenarios**:

1. **Given** a visitor navigates to `/industries`, **When** the page loads, **Then** they see a grid of all active industry cards, each displaying: name, hero image (or placeholder), short description, and a "Learn More" CTA.
2. **Given** the industries are ordered by display order, **When** the page renders, **Then** the industries appear in the configured sequence (not alphabetical or arbitrary).
3. **Given** a visitor clicks an industry card, **When** the navigation completes, **Then** they land on that industry's dedicated detail page at `/industries/{slug}`.
4. **Given** no active industries exist, **When** the page loads, **Then** a friendly empty-state message is shown rather than a blank grid.
5. **Given** the industries page is loaded, **When** the browser tab title and meta description are inspected, **Then** both contain relevant keywords about industry-specific packaging solutions (not generic site defaults).

---

### User Story 2 - Explore a Single Industry Detail Page (Priority: P2)

A cosmetics brand manager arrives at the Cosmetics Packaging industry page (via the overview,
search engine, or a direct link). They read about the specific packaging challenges in their
industry, the solutions MaigePack offers, the key benefits, and then browse recommended packaging
products tailored to their sector.

**Why this priority**: The industry detail page is the primary conversion page for industry-led
traffic — it must persuade a sector-specific buyer that MaigePack understands their needs.

**Independent Test**: Navigate directly to `/industries/cosmetics-packaging`. Verify the page
shows the industry name, description, at least one key benefit, at least one packaging solution,
and a list of recommended products. Verify the page title matches the industry's SEO meta title.

**Acceptance Scenarios**:

1. **Given** a visitor arrives at `/industries/cosmetics-packaging`, **When** the page loads, **Then** they see: industry name, hero image, description, key benefits list, common challenges, packaging solutions, and recommended products for that industry.
2. **Given** an industry has a hero image configured, **When** the page renders, **Then** the hero image is displayed prominently at the top of the page.
3. **Given** an industry has no hero image, **When** the page renders, **Then** a branded placeholder image is shown rather than a broken image or empty space.
4. **Given** an industry has associated recommended products, **When** the page loads, **Then** those products are shown in a grid with name, image, short description, and a link to the product detail page.
5. **Given** an industry has no associated products, **When** the page loads, **Then** the recommended products section is hidden rather than showing an empty container.
6. **Given** a visitor arrives at `/industries/non-existent-slug`, **When** the page loads, **Then** a clear "Not Found" message is shown with a link back to `/industries`.

---

### User Story 3 - Discover the Right Industry via Product Pages (Priority: P3)

A visitor browsing the product catalogue sees that a product targets specific industries. They
click an industry badge on the product detail page and are taken directly to that industry's
solutions page, giving them a richer understanding of how the product fits their sector.

**Why this priority**: Creates a two-way discovery path between products and industries,
increasing engagement and cross-navigation. Relies on the product-to-industry link being accurate
and consistent.

**Independent Test**: On any product detail page, click an industry badge (e.g., "Electronics").
Verify the navigation lands on the correct industry detail page for that industry.

**Acceptance Scenarios**:

1. **Given** a visitor is on a product detail page that lists target industries, **When** they click an industry badge, **Then** they are navigated to the matching industry detail page at `/industries/{industry-slug}`.
2. **Given** an industry badge on a product page corresponds to an active industry, **When** clicked, **Then** the destination industry page loads with the correct industry content.
3. **Given** an industry badge on a product page corresponds to an industry that no longer exists or is inactive, **When** clicked, **Then** the user lands on a "Not Found" page rather than a broken or empty page.

---

### User Story 4 - Find Industry Pages via Search Engines (Priority: P4)

A packaging buyer searches Google for "cosmetics packaging solutions" and lands directly on the
Cosmetics industry page. The page has a unique, keyword-rich title and meta description that
accurately reflects the industry content, enabling strong search engine ranking.

**Why this priority**: Organic search is a primary acquisition channel. Industry pages are
high-intent landing pages that should rank for sector-specific queries.

**Independent Test**: Inspect the `<title>` and `<meta name="description">` of any industry
detail page. Verify they contain the industry name and relevant keywords, and differ between
any two industry pages.

**Acceptance Scenarios**:

1. **Given** any active industry detail page is loaded, **When** the page `<head>` is inspected, **Then** `<title>` contains the industry name and brand (e.g., "Cosmetics Packaging Solutions | MagerPack") and `<meta name="description">` reflects the industry's specific value proposition.
2. **Given** an industry has `metaTitle` and `metaDescription` values configured, **When** the page loads, **Then** those values are used verbatim for `<title>` and `<meta name="description">`.
3. **Given** an industry does not have `metaTitle` configured, **When** the page loads, **Then** a sensible fallback title is generated from the industry name (e.g., "[Industry Name] Packaging Solutions | MagerPack").
4. **Given** two different industry pages are loaded, **When** their `<title>` tags are compared, **Then** both values are unique and specific to their respective industry.

---

### Edge Cases

- What happens when an industry's `keyBenefits`, `commonChallenges`, or `packagingSolutions` lists are empty? Those sections must be hidden, not rendered as empty headings or bullet lists.
- What happens when an industry has a very long description (3,000+ characters)? The layout must not break; content should be scrollable.
- What happens when a product's industry tag does not match any active industry slug? The badge must still display, but clicking it leads to a "Not Found" page — not a crash.
- What happens when the industries API is unreachable? Both the overview page and detail pages must show a friendly error state rather than a blank screen.
- What happens when two industries have very similar names? Their slugs and SEO meta must remain distinct.
- What happens when an industry is marked inactive? It must not appear on the overview page or be reachable at its URL.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The `/industries` overview page MUST display all active industries ordered by `displayOrder`, each showing: name, hero image (or placeholder), short description, and a "Learn More" link to `/industries/{slug}`.
- **FR-002**: The `/industries/{slug}` detail page MUST display: name, hero image, description, key benefits list, common challenges, packaging solutions list, and recommended products — omitting any section whose data is empty or null.
- **FR-003**: The recommended products section on an industry detail page MUST show products whose industry association matches the industry being viewed, each as a card linking to the product detail page.
- **FR-004**: Products and industries MUST be linked via a single canonical identifier (the industry `slug`) to ensure the recommended products query returns consistent results.
- **FR-005**: Industry badges on product detail pages MUST link to `/industries/{industry-slug}`, using the canonical industry slug — not a free-text label.
- **FR-006**: Every active industry MUST have a unique `<title>` and `<meta name="description">` — using `metaTitle`/`metaDescription` when set, with a generated fallback when not.
- **FR-007**: Visiting an inactive or non-existent industry slug MUST render a "Not Found" state with a link back to `/industries`, not a blank or broken page.
- **FR-008**: The recommended products section MUST be hidden when no products are associated with the industry.
- **FR-009**: The `/industries` overview page MUST set its own unique `<title>` and `<meta name="description">` covering industry-specific packaging.
- **FR-010**: All industry pages MUST be fully functional and readable on mobile screen widths from 320px upward.
- **FR-011**: Industry pages MUST include a breadcrumb trail: Home → Industries → [Industry Name] (detail pages only).

### Key Entities

- **Industry**: The central entity for these pages. Relevant attributes: `name`, `slug`, `shortDescription`, `description`, `heroImageUrl`, `iconUrl`, `keyBenefits`, `commonChallenges`, `packagingSolutions`, `recommendedProductTypes`, `metaTitle`, `metaDescription`, `seoKeywords`, `active`, `displayOrder`.
- **Industry–Product Link**: The relationship between an industry and the products shown on its detail page. MUST be based on the industry `slug` as the canonical identifier, stored consistently in both the industry record and the product's industry association field.
- **Recommended Product**: A summary view of a product shown on an industry page — `name`, `slug`, `imageUrl`, `shortDescription`, `category`.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: A visitor can browse all industry pages and navigate from the overview to any individual industry detail page in two clicks or fewer.
- **SC-002**: Every active industry detail page displays at least its name, description, one benefit, and one packaging solution — verified across all seeded industries.
- **SC-003**: Recommended products on an industry page are accurate — all displayed products have the viewing industry in their industry associations, verified for all seeded industries.
- **SC-004**: 100% of active industry detail pages have a unique, non-empty `<title>` and `<meta name="description">`.
- **SC-005**: Industry pages load and display content within 2 seconds on a standard broadband connection.
- **SC-006**: Clicking an industry badge on any product page navigates to the correct industry detail page 100% of the time.
- **SC-007**: Inactive or non-existent industry slugs return a "Not Found" state — verified across at least three invalid slugs with zero blank screens.

## Assumptions

- The existing `/api/public/industries` and `/api/public/industries/{slug}` backend endpoints are used as-is; no new industry API endpoints are required.
- The product-to-industry link currently stores free-text labels in `Product.targetIndustries`; this feature requires standardising those values to use industry `slug` values as the canonical identifier. This is a data fix, not a new feature — it is in scope as a prerequisite.
- The existing `/api/public/products/industry/{industry}` endpoint is updated to accept and match against industry `slug` values (consistent with the standardisation above).
- No user authentication is required to view any industry page.
- The four seeded industries (Electronics, Cosmetics, Jewellery, Fragrance) are the initial content; the admin product management feature (002) will later allow adding more industries via the admin panel.
- Industry page content (benefits, challenges, solutions) is managed via the backend data model; no CMS or rich-text editor is required for v1.
- `seoKeywords` from the Industry model are used to populate a `<meta name="keywords">` tag, even though its direct SEO impact is minimal — it is already in the data model.
- Mobile-first responsive layout is required; the same page is served to all devices (no separate mobile version).
