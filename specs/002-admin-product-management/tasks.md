# Tasks: Admin Product Management (002)

**Input**: `specs/002-admin-product-management/plan.md` + `spec.md`
**Branch**: `002-admin-product-management`

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies on each other)
- **[Story]**: User story this task belongs to (US1–US5)

---

## Phase 1: Setup

**Purpose**: Shared scaffolding needed before any user story work begins.

- [ ] T001 Create `frontend/middleware/` directory and `frontend/composables/` directory (scaffolding only)
- [ ] T002 Create `frontend/pages/admin/` and `frontend/pages/admin/products/[id]/` directory structure (scaffolding only)

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Backend infrastructure and frontend auth layer that EVERY user story depends on. Must complete before Phases 3–7.

- [ ] T003 Create `ProductRequest.java` DTO in `backend/src/main/java/com/maigepack/backend/api/admin/ProductRequest.java` — all Product fields, `@NotBlank` on `name` and `category`
- [ ] T004 Create `ProductSummaryResponse.java` DTO in `backend/src/main/java/com/maigepack/backend/api/admin/ProductSummaryResponse.java` — fields: id, name, slug, category, active, displayOrder, createdAt
- [ ] T005 Add `findAllProductsWithFilters` query method to `backend/src/main/java/com/maigepack/backend/repository/ProductRepository.java` — same as `findProductsWithFilters` but without `active = true` filter so admins see all products
- [ ] T006 [P] Create `frontend/middleware/admin-auth.ts` — reads `adminToken` from localStorage, decodes JWT `exp`, redirects to `/admin/login` if missing or expired
- [ ] T007 [P] Create `frontend/composables/useAdminApi.ts` — wraps `$fetch` to auto-inject `Authorization: Bearer <token>` header; on 401 clears token and calls `navigateTo('/admin/login')`

**Checkpoint**: DTOs, repository query, auth middleware, and API composable are ready. User story implementation can begin.

---

## Phase 3: User Story 1 — Secure Admin Login (P1) 🎯 MVP

**Goal**: Admin can navigate to `/admin`, get redirected to `/admin/login`, log in with credentials, and be redirected to the dashboard. Invalid credentials show an error. Logout clears the session.

**Independent Test**: Navigate to `http://localhost:3333/admin` → verify redirect to `/admin/login`. Log in with `admin`/`admin` → verify redirect to `/admin/products`. Enter wrong password → verify error message shown.

- [ ] T008 [US1] Create `frontend/pages/admin/login.vue` — form with username + password fields; on submit POST to `/api/admin/auth/login`, store token as `adminToken` in localStorage, navigate to `/admin/products`; show "Invalid credentials" message on 401
- [ ] T009 [US1] Create `frontend/pages/admin/index.vue` — applies `admin-auth` middleware, immediately redirects to `/admin/products`

**Checkpoint**: Login flow fully functional. Unauthenticated `/admin` access redirects to login.

---

## Phase 4: User Story 2 — View and Search the Product List (P2)

**Goal**: Logged-in admin sees a paginated, searchable, filterable table of all products at `/admin/products`.

**Independent Test**: Log in, navigate to `/admin/products`. Verify table shows Name, Category, Active status, Display Order, Created At. Type a product name in the search box and verify the list filters. Select a category and verify filtering works.

- [ ] T010 [US2] Create `AdminProductController.java` in `backend/src/main/java/com/maigepack/backend/api/admin/AdminProductController.java` with `GET /api/admin/products` endpoint — paginated, accepts `page`, `size`, `search`, `category`, `sortBy`, `sortDirection` query params; returns `Page<ProductSummaryResponse>` using `findAllProductsWithFilters`
- [ ] T011 [US2] Create `frontend/pages/admin/products/index.vue` — table with columns: Name, Category, Active badge (green/red), Display Order, Created At, Edit/Delete actions; search input (300ms debounce); category filter dropdown populated from `/api/public/products/categories`; pagination controls; "Add Product" button → `/admin/products/new`; Logout button → clear localStorage + navigate to `/admin/login`

**Checkpoint**: Product list page fully functional with search, filter, and pagination.

---

## Phase 5: User Story 3 — Create a New Product (P3)

**Goal**: Admin can fill out a form at `/admin/products/new`, upload an image, save the product, and see it immediately on the public `/products` page.

**Independent Test**: Log in, navigate to `/admin/products/new`, fill in Name ("Test Box") and Category ("Rigid Boxes"), click Save. Navigate to public `/products` and verify "Test Box" appears.

- [ ] T012 [US3] Add `POST /api/admin/products` endpoint to `AdminProductController.java` — maps `ProductRequest` to `Product` entity, auto-generates unique slug from name (lowercase, replace non-alphanumeric with `-`, append `-2`/`-3` etc. if slug already exists), saves via `productRepository.save()`, returns `201 Created` with full product JSON
- [ ] T013 [US3] Create `frontend/pages/admin/products/new.vue` — full product form covering all `ProductRequest` fields: text inputs for name/slug/shortDescription/description/category/materialOptions/finishOptions/sizeRange/leadTime; number input for minimumOrderQuantity and displayOrder; active toggle; image upload widget (file input → POST `/api/admin/uploads` with Bearer token → populate imageUrl + show `<img>` preview); dynamic add/remove rows for features, additionalImages, targetIndustries; key-value pair editor for specifications; client-side required check on name + category before submit; on success navigate to `/admin/products`

**Checkpoint**: Admin can create a new product with an image and see it live on the public catalogue.

---

## Phase 6: User Story 4 — Edit an Existing Product (P4)

**Goal**: Admin can open an existing product, see all fields pre-populated, edit any field, and save — with changes immediately visible on the public product detail page.

**Independent Test**: Log in, find a product in the list, click Edit. Verify all fields are pre-populated. Change the short description, save. Visit the public product page at `/products/[slug]` and verify the updated text appears.

- [ ] T014 [US4] Add `GET /api/admin/products/{id}` and `PUT /api/admin/products/{id}` endpoints to `AdminProductController.java` — GET returns full `Product` JSON (404 if not found); PUT maps `ProductRequest` onto the existing entity (preserving `createdAt`), saves and returns updated product JSON
- [ ] T015 [US4] Create `frontend/pages/admin/products/[id]/edit.vue` — same form layout as `new.vue`; on `onMounted` call `GET /api/admin/products/{id}` and pre-populate all fields; on save call `PUT /api/admin/products/{id}`; on success navigate to `/admin/products`; link Edit buttons in the product list page to `/admin/products/{id}/edit`

**Checkpoint**: Admin can edit any product field including swapping the image. Changes are live immediately.

---

## Phase 7: User Story 5 — Delete a Product (P5)

**Goal**: Admin can permanently delete a product after confirming a prompt.

**Independent Test**: Log in, find a product, click Delete. Verify a confirmation dialog appears. Confirm deletion. Verify the product no longer appears in the admin list or on the public `/products` page.

- [ ] T016 [US5] Add `DELETE /api/admin/products/{id}` endpoint to `AdminProductController.java` — calls `productRepository.deleteById(id)` (404 if not found), returns `204 No Content`
- [ ] T017 [US5] Wire up Delete button in `frontend/pages/admin/products/index.vue` — `window.confirm('Delete this product?')` → call `DELETE /api/admin/products/{id}` via `useAdminApi` → refresh the product list on success

**Checkpoint**: Full CRUD is complete. All five user stories are independently testable.

---

## Phase 8: Polish & Cross-Cutting Concerns

- [ ] T018 Add `definePageMeta({ middleware: 'admin-auth' })` to all admin pages that need protection: `admin/index.vue`, `admin/products/index.vue`, `admin/products/new.vue`, `admin/products/[id]/edit.vue` — verify login page does NOT have this meta
- [ ] T019 [P] Add loading states and error banners to all admin pages — spinner while fetching product list/detail; error message banner on save/delete failure
- [ ] T020 [P] Validate image upload file type on the frontend in `new.vue` and `edit.vue` — check `file.type` against `image/jpeg`, `image/png`, `image/webp`, `image/gif`; show error and abort upload if type is not allowed (FR-010)
- [ ] T021 Smoke test end-to-end: login → create product with image → verify on public `/products` → edit short description → verify on public `/products/[slug]` → delete → verify gone
- [ ] T022 Push branch and open PR to `develop`

---

## Dependencies & Execution Order

```
Phase 1 (Setup)
    └── Phase 2 (Foundational)  ← BLOCKS everything below
            ├── Phase 3 (US1 Login)         ← no story dependencies
            ├── Phase 4 (US2 List)          ← depends on T010 (backend list endpoint)
            ├── Phase 5 (US3 Create)        ← depends on T012 (backend create endpoint)
            ├── Phase 6 (US4 Edit)          ← depends on T014 (backend get/update endpoints)
            └── Phase 7 (US5 Delete)        ← depends on T016 (backend delete endpoint)
                    └── Phase 8 (Polish)
```

### Parallel opportunities within phases

- **Phase 2**: T003, T004, T005 backend work can run in parallel with T006, T007 frontend work
- **Phase 4+**: Once Phase 2 is done, all backend endpoints (T010, T012, T014, T016) can be written in parallel in `AdminProductController.java` — they're all in the same file but independent methods
- **Phase 8**: T019 and T020 can run in parallel (different files)

---

## Implementation Strategy

### MVP (Phases 1–3 only)
Complete Setup + Foundational + Login. Delivers a protected admin area with working authentication. Validates the JWT middleware and auth flow before building any product UI.

### Incremental delivery
- After Phase 4: Admin can see all products → useful for read-only review
- After Phase 5: Admin can add products → catalogue can grow
- After Phase 6: Admin can edit products → catalogue can be maintained
- After Phase 7: Full CRUD complete → catalogue hygiene possible
