# Feature Specification: Blog and Content Pages

**Feature Branch**: `004-blog-content-pages`
**Created**: 2026-04-03
**Status**: Draft

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Browse the Blog Index (Priority: P1)

A packaging buyer or industry professional visits the MaigePack blog to browse articles and
guides. They see a paginated list of published articles, each showing a cover image, title,
excerpt, category, publication date, and a "Read More" link. They can filter articles by
category to narrow down to topics relevant to their work.

**Why this priority**: The blog index is the entry point for all content. Without it, no
article is discoverable and the entire feature has no value.

**Independent Test**: Navigate to `/blog`. Verify that published articles are listed with
title, excerpt, cover image, category, and date. Filter by a category and verify only matching
articles are shown.

**Acceptance Scenarios**:

1. **Given** a visitor navigates to `/blog`, **When** the page loads, **Then** they see a paginated list of published articles, each showing: cover image (or placeholder), title, excerpt, category badge, publication date, and a "Read More" link.
2. **Given** articles are ordered by publication date, **When** the page renders, **Then** the most recently published article appears first.
3. **Given** a visitor selects a category filter, **When** the filter is applied, **Then** only articles belonging to that category are shown.
4. **Given** more articles exist than fit on one page, **When** the visitor reaches the bottom, **Then** pagination controls allow them to navigate to the next and previous pages.
5. **Given** no published articles exist yet, **When** the page loads, **Then** a friendly empty-state message is shown rather than a blank or broken page.
6. **Given** the blog index is loaded, **When** the page `<head>` is inspected, **Then** `<title>` and `<meta name="description">` contain relevant keywords about packaging guides and insights.

---

### User Story 2 - Read a Full Article (Priority: P2)

A visitor clicks on an article from the blog index (or arrives directly from a search engine
result) and reads the full article content. The page includes the article title, cover image,
author name, publication date, category, full body content, and related articles at the bottom
to encourage further reading.

**Why this priority**: Reading full articles is the core value of the blog. It delivers the
SEO content, educates buyers, and keeps visitors engaged on the site.

**Independent Test**: Click any article on the blog index. Verify the full article loads with
title, author, date, cover image, and body content. Verify the page `<title>` matches the
article title. Verify related articles are shown at the bottom.

**Acceptance Scenarios**:

1. **Given** a visitor clicks "Read More" on an article, **When** the article page loads, **Then** they see: title, cover image, author name, publication date, category badge, full article body, and up to 3 related articles.
2. **Given** an article has a cover image, **When** the page renders, **Then** the image is displayed prominently at the top and used as the Open Graph image for social sharing.
3. **Given** an article has no cover image, **When** the page renders, **Then** a branded placeholder image is used rather than a broken image or empty space.
4. **Given** a visitor shares the article link on social media, **When** the link is previewed, **Then** the preview shows the article title, excerpt, and cover image (Open Graph tags populated).
5. **Given** a visitor arrives at `/blog/non-existent-slug`, **When** the page loads, **Then** a "Not Found" message is shown with a link back to `/blog`.
6. **Given** any published article page is loaded, **When** the `<head>` is inspected, **Then** `<title>` contains the article title and brand name, and `<meta name="description">` contains the article excerpt or summary.

---

### User Story 3 - Discover Articles via Related Products and Industries (Priority: P3)

A visitor reading a product detail page or industry solutions page sees a "Related Guides"
section showing blog articles relevant to that product or industry. Clicking an article takes
them to the full article, deepening their engagement and reinforcing MaigePack's expertise.

**Why this priority**: Cross-linking content to products and industries drives time-on-site,
strengthens topical authority for SEO, and moves buyers closer to a quote request.

**Independent Test**: On a product detail page, scroll to the bottom. Verify a "Related Guides"
section appears with at least one article card linked by category or tag. Click the article and
verify it loads correctly.

**Acceptance Scenarios**:

1. **Given** a visitor is on a product detail page, **When** articles tagged with that product's category exist, **Then** up to 3 related articles are shown in a "Related Guides" section.
2. **Given** a visitor is on an industry solutions page, **When** articles tagged with that industry exist, **Then** up to 3 related articles are shown in a "Related Guides" section.
3. **Given** no related articles exist for a product or industry, **When** the page renders, **Then** the "Related Guides" section is hidden rather than showing an empty container.
4. **Given** a visitor clicks a related article card, **When** the navigation completes, **Then** they land on the correct full article page.

---

### User Story 4 - Admin Creates and Publishes an Article (Priority: P4)

A logged-in admin navigates to the blog section of the admin panel, creates a new article by
entering a title, body content, excerpt, cover image, category, and tags. They can save a
draft and preview it before publishing. Once published, the article immediately appears on the
public blog.

**Why this priority**: Without admin authoring, no blog content can be added. This story
unlocks all public-facing blog stories.

**Independent Test**: Log in as admin, navigate to `/admin/blog/new`, fill in all required
fields, upload a cover image, set status to "Published", and save. Navigate to the public `/blog`
and verify the new article appears at the top of the list.

**Acceptance Scenarios**:

1. **Given** a logged-in admin fills in the title, body, excerpt, and category and sets status to "Published", **When** they save, **Then** the article immediately appears on the public blog index.
2. **Given** an admin saves an article with status "Draft", **When** they save, **Then** the article does NOT appear on the public blog but IS visible in the admin article list.
3. **Given** an admin leaves the title or body blank, **When** they attempt to save, **Then** the form highlights the missing field and does not submit.
4. **Given** an admin uploads a cover image, **When** the upload succeeds, **Then** a preview of the image is shown in the form before saving.
5. **Given** an admin saves an article, **When** the save completes, **Then** the article is accessible at `/blog/{slug}` where the slug is auto-generated from the title.
6. **Given** an admin edits a previously published article and saves, **When** the save completes, **Then** the updated content is immediately visible on the public article page.

---

### User Story 5 - Find Articles via Search Engines (Priority: P5)

A packaging professional searches Google for "rigid box packaging guide" and lands directly
on a MaigePack article. The page has a rich, keyword-optimised title, meta description, and
structured Open Graph tags that help it rank well and display attractively in search results
and social previews.

**Why this priority**: SEO is the primary purpose of the blog. Every published article must
be fully optimised for organic discovery.

**Independent Test**: Inspect the `<head>` of any published article page. Verify unique
`<title>`, `<meta name="description">`, `og:title`, `og:description`, `og:image`, and
`og:type` tags are present and populated with article-specific values.

**Acceptance Scenarios**:

1. **Given** any published article page is loaded, **When** the `<head>` is inspected, **Then** `og:type` is set to `article`, `og:title` matches the article title, `og:description` matches the excerpt, and `og:image` is set to the cover image URL.
2. **Given** an article has a custom meta description set by the admin, **When** the page loads, **Then** that value is used verbatim for `<meta name="description">`.
3. **Given** no custom meta description is set, **When** the page loads, **Then** the article excerpt is used as the fallback `<meta name="description">`.
4. **Given** two different article pages are loaded, **When** their `<title>` tags are compared, **Then** both values are unique and specific to their respective articles.

---

### Edge Cases

- What happens when an article body is very long (10,000+ characters)? Layout must not break; content should be scrollable with good readability.
- What happens when a cover image URL is broken? A branded placeholder must be shown rather than a broken image icon.
- What happens when an article is unpublished (set back to draft) by the admin? It must immediately disappear from the public blog without requiring a cache purge.
- What happens when two articles have the same title? The slug must be made unique (e.g., by appending a number) to avoid URL collisions.
- What happens when an admin deletes an article that is linked from another page? The destination returns "Not Found" — no cascade updates to linking pages are required for v1.
- What happens if the blog API is unreachable? The index and article pages must show a friendly error state rather than a blank screen.
- What happens when an article has no tags or category? It still appears on the blog index without a category badge, and is simply excluded from filtered views.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The `/blog` index page MUST display all published articles ordered by publication date (newest first), each showing: cover image (or placeholder), title, excerpt, category, publication date, and a "Read More" link.
- **FR-002**: The blog index MUST support filtering by category and pagination; page size is a configuration concern for the planning phase.
- **FR-003**: Each article MUST be accessible at `/blog/{slug}` where slug is unique and derived from the article title.
- **FR-004**: The article detail page MUST display: title, cover image, author name, publication date, category, full body content, and up to 3 related articles.
- **FR-005**: Articles with status "Draft" MUST NOT appear on any public-facing page or be reachable via their public URL.
- **FR-006**: Articles with status "Published" MUST appear on the public blog index and be reachable at their URL immediately after saving.
- **FR-007**: Every published article page MUST have a unique `<title>`, `<meta name="description">`, and Open Graph tags (`og:title`, `og:description`, `og:image`, `og:type: article`).
- **FR-008**: When a custom meta description is provided, it MUST be used; otherwise the article excerpt MUST be used as the fallback.
- **FR-009**: The blog index page MUST have its own unique `<title>` and `<meta name="description">`.
- **FR-010**: Product detail and industry pages MUST show a "Related Guides" section with up to 3 articles matched by category or tag; the section MUST be hidden when no matching articles exist.
- **FR-011**: Admins MUST be able to create, edit, and delete articles via the admin panel, with support for: title, slug (auto-generated, overridable), excerpt, body content, cover image (via existing upload endpoint), category, tags, author name, publication date, and status (Draft / Published).
- **FR-012**: The admin article list MUST show all articles (draft and published) with title, status, category, and publication date.
- **FR-013**: Visiting a non-existent or unpublished article slug MUST return a "Not Found" state with a link to `/blog`.
- **FR-014**: A "Blog" navigation link MUST be added to the site header, pointing to `/blog`.
- **FR-015**: All public blog pages MUST be fully functional and readable on screen widths from 320px upward.

### Key Entities

- **Article**: The core content entity. Attributes: `title`, `slug`, `excerpt`, `body` (rich text/markdown), `coverImageUrl`, `authorName`, `category`, `tags` (list of strings), `status` (Draft / Published), `publishedAt`, `createdAt`, `updatedAt`, `metaDescription` (optional override).
- **Category**: A classification label for grouping articles (e.g., "Packaging Tips", "Industry Guides", "Design Inspiration"). Stored as a simple string on the Article entity for v1.
- **Tag**: A free-text keyword associated with an article for cross-linking to products and industries (e.g., "rigid-boxes", "cosmetics-packaging"). Stored as a list of strings on the Article.
- **Related Article**: A summary view of another article shown at the bottom of an article page — `title`, `slug`, `coverImageUrl`, `excerpt`, `category`.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: An admin can create and publish a complete article with a cover image in under 5 minutes.
- **SC-002**: All published articles appear on the blog index within 3 seconds of being saved as "Published".
- **SC-003**: 100% of published article pages have a unique `<title>`, `<meta name="description">`, and `og:image` tag.
- **SC-004**: The blog index and article detail pages load and display content within 2 seconds on a standard broadband connection.
- **SC-005**: Filtering the blog by category returns only matching articles with zero false positives, verified across all available categories.
- **SC-006**: Related Guides sections on product and industry pages show accurate articles — all shown articles share a tag or category with the host page, verified for at least 5 pages.
- **SC-007**: Draft articles are not reachable at their public URL — verified by attempting direct access to 3 draft article slugs and confirming "Not Found" is returned each time.

## Assumptions

- This is a fully greenfield feature — no Article entity, API, or frontend pages exist yet.
- Body content is stored and delivered as Markdown; the frontend renders it as HTML. A WYSIWYG editor is out of scope for v1 — the admin enters Markdown directly.
- A single author name per article (plain text string) is sufficient for v1; a full Author entity with profile pages is out of scope.
- Categories are free-text strings on the Article entity for v1; a separate managed Category entity is out of scope.
- Tags are free-text strings stored as a list; they are used to match articles to products and industries using the same canonical slug values (consistent with the industry-to-product slug standardisation in spec 003).
- The existing admin JWT authentication is reused for blog admin operations; no additional auth setup is required.
- The existing media upload endpoint is reused for cover image uploads.
- Comment sections and social sharing buttons are out of scope for v1.
- An RSS feed is out of scope for v1.
- A "Blog" link is added to the site header navigation; no mega-menu or category sub-navigation in the header is required for v1.
- The public blog is read-only; no user-generated content or subscriptions are in scope.
