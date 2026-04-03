# Feature Specification: Admin Product Management

**Feature Branch**: `002-admin-product-management`
**Created**: 2026-04-03
**Status**: Draft

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Secure Admin Login (Priority: P1)

An admin user navigates to the admin area of the site. They are presented with a login form and
must authenticate with their username and password before accessing any management functionality.
Unauthenticated visitors who attempt to access admin pages are redirected to the login screen.

**Why this priority**: Every other admin capability depends on authenticated access. Without a
working login flow, the admin panel cannot be used at all.

**Independent Test**: Navigate to `/admin`. Verify you are redirected to `/admin/login`. Enter
valid credentials and verify you are redirected to the admin dashboard. Enter invalid credentials
and verify an error message is shown with no access granted.

**Acceptance Scenarios**:

1. **Given** a visitor navigates to any `/admin/*` page without being logged in, **When** the page loads, **Then** they are redirected to `/admin/login`.
2. **Given** an admin user enters a valid username and password on the login form, **When** they submit, **Then** they are redirected to the admin dashboard and their session is preserved across page refreshes.
3. **Given** an admin user enters an incorrect password, **When** they submit, **Then** an error message is displayed and they remain on the login page.
4. **Given** a logged-in admin user clicks "Logout", **When** the action completes, **Then** their session is terminated and they are redirected to `/admin/login`; navigating back does not restore access.
5. **Given** a session token has expired (24 hours), **When** the admin attempts any action, **Then** they are automatically redirected to `/admin/login`.

---

### User Story 2 - View and Search the Product List (Priority: P2)

A logged-in admin navigates to the products section and sees all products in a paginated,
searchable table. They can filter by category, search by name, and sort by display order or
creation date, giving them a clear overview of the entire product catalogue.

**Why this priority**: The product list is the entry point for all other product management
actions (edit, delete, reorder). An admin must be able to find any product quickly before acting
on it.

**Independent Test**: Log in as admin and navigate to `/admin/products`. Verify the product list
loads, shows product name, category, active status, and display order. Search for a product by
name and verify the list filters correctly.

**Acceptance Scenarios**:

1. **Given** a logged-in admin navigates to `/admin/products`, **When** the page loads, **Then** they see a paginated list of all products showing: name, category, active/inactive status, display order, and creation date.
2. **Given** the admin types in the search box, **When** they enter a product name or partial name, **Then** the list filters in real time to show only matching products.
3. **Given** the admin selects a category filter, **When** the filter is applied, **Then** only products from that category are shown.
4. **Given** the product list has more than one page of results, **When** the admin navigates between pages, **Then** each page displays the correct subset of products without losing the active search/filter state.
5. **Given** the admin clicks a column header (e.g., "Display Order"), **When** the sort is applied, **Then** products are reordered accordingly.

---

### User Story 3 - Create a New Product (Priority: P3)

A logged-in admin fills in a form to add a new product to the catalogue. They enter the product
name, description, category, specifications, and upload a primary image. On save, the product
becomes immediately visible on the public product catalogue.

**Why this priority**: Creating products is the primary write operation of the admin panel.
Without it, the catalogue cannot be grown or updated.

**Independent Test**: Log in, navigate to `/admin/products/new`, fill in all required fields,
upload an image, and save. Navigate to the public `/products` page and verify the new product
appears.

**Acceptance Scenarios**:

1. **Given** a logged-in admin fills in all required fields (name, category, description) and clicks "Save", **When** the save completes, **Then** the new product appears in the admin product list and on the public catalogue.
2. **Given** an admin leaves a required field blank, **When** they attempt to save, **Then** the form highlights the missing field with a clear error message and does not submit.
3. **Given** an admin enters a product name that is identical to an existing product's name, **When** they save, **Then** the system auto-generates a unique URL slug and saves successfully.
4. **Given** an admin uploads a primary image during product creation, **When** the upload succeeds, **Then** the image is shown as a preview before saving, and is displayed on the public product page after saving.
5. **Given** an admin uploads a file that is not an image (e.g., a PDF), **When** they attempt to upload, **Then** the upload is rejected with a clear error message.
6. **Given** an admin creates a product with `active` set to false, **When** the product is saved, **Then** it does not appear on the public catalogue but is visible in the admin list.

---

### User Story 4 - Edit an Existing Product (Priority: P4)

A logged-in admin selects a product from the list and edits its details — updating the
description, changing the image, reordering it, or toggling it active/inactive. Changes are
reflected on the public product pages immediately after saving.

**Why this priority**: Products need to be kept accurate and up to date. Editing is the most
frequent ongoing admin task after the initial catalogue is built.

**Independent Test**: Log in, find an existing product, click Edit, change the short description,
save, then visit the public product page and verify the updated text is shown.

**Acceptance Scenarios**:

1. **Given** an admin opens the edit form for an existing product, **When** the form loads, **Then** all current product values are pre-populated in their respective fields.
2. **Given** an admin changes the product description and clicks "Save", **When** the save completes, **Then** the updated description is immediately visible on the public product detail page.
3. **Given** an admin uploads a new primary image for a product, **When** the upload and save complete, **Then** the new image replaces the old one on the public page.
4. **Given** an admin adds a new entry to the additional images list, **When** the save completes, **Then** the new image appears in the product gallery on the public page.
5. **Given** an admin toggles a product to inactive, **When** the save completes, **Then** the product no longer appears on the public catalogue but remains editable in the admin panel.
6. **Given** an admin changes the display order of a product, **When** the save completes, **Then** the product appears in the updated position on the public product listing.

---

### User Story 5 - Delete a Product (Priority: P5)

A logged-in admin permanently deletes a product from the catalogue. Before deletion, a
confirmation prompt is shown to prevent accidental removal.

**Why this priority**: Deletion is a destructive, irreversible action. It is lower priority than
the core CRUD operations but necessary for catalogue hygiene.

**Independent Test**: Log in, select a product, click Delete, confirm the prompt, and verify the
product no longer appears in the admin list or on the public catalogue.

**Acceptance Scenarios**:

1. **Given** an admin clicks "Delete" on a product, **When** the action is triggered, **Then** a confirmation dialog appears asking them to confirm the deletion.
2. **Given** the admin confirms the deletion, **When** the confirmation is submitted, **Then** the product is permanently removed from the admin list and the public catalogue; its public URL returns a "Not Found" response.
3. **Given** the admin dismisses the confirmation dialog, **When** the dialog is closed, **Then** the product is not deleted and the list remains unchanged.

---

### Edge Cases

- What happens when two admins edit the same product simultaneously? The last save wins; no merge conflict handling is required for v1.
- What happens when an image upload fails mid-way through product creation? The admin should be able to retry the upload without losing other form data.
- What happens if the admin's session expires while they are mid-edit? On save attempt, they should be redirected to login; form data is lost (acceptable for v1).
- What happens when a product with many additional images (10+) is loaded? The form must handle long lists without UI breakdown.
- What happens when the product name contains special characters (e.g., `<`, `>`, `&`)? They must be safely escaped before display and storage.
- What happens when the admin deletes a product that is referenced in a customer's saved quote? The quote submission record is unaffected (product name is stored as plain text in the form submission).

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The admin area MUST be protected; unauthenticated users MUST be redirected to `/admin/login`.
- **FR-002**: Admin users MUST be able to log in with a username and password and receive a session that persists for 24 hours.
- **FR-003**: Admin users MUST be able to log out, immediately terminating their session.
- **FR-004**: The admin product list MUST display all products with name, category, active status, display order, and creation date.
- **FR-005**: The admin MUST be able to search products by name and filter by category.
- **FR-006**: The admin MUST be able to create a new product with: name, slug (auto-generated from name), short description, description, category, primary image, additional images, features list, specifications (key-value pairs), material options, finish options, size range, minimum order quantity, lead time, target industries, active flag, and display order.
- **FR-007**: Product name and category MUST be required fields; all other fields are optional.
- **FR-008**: The system MUST auto-generate a unique URL slug from the product name; the admin MAY override it manually.
- **FR-009**: The admin MUST be able to upload images via the existing upload endpoint; uploaded image URLs MUST be assignable to `imageUrl` and `additionalImages`.
- **FR-010**: File uploads MUST be restricted to image types (JPEG, PNG, WebP, GIF); non-image files MUST be rejected with a clear error.
- **FR-011**: The admin MUST be able to edit any field of an existing product and save changes.
- **FR-012**: Saved changes MUST be immediately reflected on the public-facing product pages.
- **FR-013**: The admin MUST be able to toggle a product between active and inactive states.
- **FR-014**: Inactive products MUST NOT appear on the public catalogue or product detail pages.
- **FR-015**: The admin MUST be able to permanently delete a product, with a confirmation step before the deletion is executed.
- **FR-016**: All admin actions (create, update, delete) MUST require a valid authenticated session; expired or missing sessions MUST result in a redirect to login.

### Key Entities

- **Product**: The core manageable entity. All fields on the existing Product model are in scope for admin create and edit — name, slug, category, descriptions, images, features, specifications, material/finish/size options, MOQ, lead time, industries, active flag, display order.
- **Admin User**: The authenticated operator. Has a username and hashed password. A single admin role is sufficient for v1.
- **Media Object**: An uploaded image asset. Created via the existing upload endpoint. Its URL is referenced in product image fields.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: An admin can log in, create a complete product with an image, and see it live on the public catalogue — all within 5 minutes.
- **SC-002**: An admin can find any product in the admin list within 10 seconds using the search or filter.
- **SC-003**: Saving edits to a product reflects the changes on the public product page within 3 seconds.
- **SC-004**: 100% of admin routes are inaccessible to unauthenticated users — verified by attempting direct URL access without a session.
- **SC-005**: Image uploads complete and display as a preview within 5 seconds for files up to 5 MB.
- **SC-006**: Deleting a product results in a "Not Found" response on its public URL within 3 seconds of confirmation.

## Assumptions

- The existing JWT-based admin authentication (`POST /api/admin/auth/login`) is reused as-is; no changes to the auth backend are required.
- A single admin role is sufficient for v1 — no granular role-based permissions (e.g., read-only vs. full access) are needed.
- The existing media upload endpoint (`POST /api/admin/uploads`) is reused for image uploads; no changes to the upload backend are required.
- The admin UI is a new section of the existing Nuxt 3 frontend under the `/admin` route prefix, not a separate application.
- Mobile support for the admin panel is out of scope for v1 — desktop browsers (1024px+) are the target.
- Bulk import/export of products is out of scope for v1.
- Audit logging (who changed what and when) is out of scope for v1.
- The admin panel does not need to manage admin user accounts (add/remove admins) in v1 — the seeded default admin user is sufficient.
- The public catalogue is automatically updated when products are saved; no separate "publish" step is required.
