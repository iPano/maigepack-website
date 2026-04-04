# Implementation Plan: Admin Product Management

**Branch**: `002-admin-product-management` | **Date**: 2026-04-04 | **Spec**: `specs/002-admin-product-management/spec.md`

## Summary

Build an admin panel at `/admin` that lets an authenticated admin log in, then create, read,
update, and delete products. The backend needs a new `AdminProductController` exposing CRUD
endpoints under `/api/admin/products`. The frontend needs four new Nuxt pages (`/admin/login`,
`/admin/products`, `/admin/products/new`, `/admin/products/[id]/edit`) protected by a
client-side auth middleware. Existing infrastructure — JWT auth, upload endpoint, Product entity,
ProductRepository — is reused unchanged.

## Technical Context

**Language/Version**: Java 17 (backend), TypeScript / Nuxt 3.21 (frontend)
**Primary Dependencies**: Spring Boot 3.2.5, Spring Data JPA, Spring Security + JwtAuthFilter, Nuxt 3, Tailwind CSS
**Storage**: PostgreSQL (prod), H2 (dev) — schema already complete via Flyway V1–V3
**Testing**: Gradle (`./gradlew test`), manual browser testing
**Target Platform**: Railway (backend), Vercel (frontend SPA)
**Project Type**: Web service (backend REST API) + SPA (frontend)
**Performance Goals**: Save/load product within 3 s (SC-003)
**Constraints**: Admin panel desktop-only (1024px+); no mobile breakpoints required
**Scale/Scope**: Single admin user, catalogue of ~50–200 products

## Constitution Check

| Principle | Status | Notes |
|-----------|--------|-------|
| I – API Contract Stability | ✅ PASS | New endpoints only; no existing public endpoints changed |
| II – Design System Authority | ✅ PASS | Admin UI uses existing Tailwind tokens; no hardcoded colours |
| III – Security Boundaries | ✅ PASS | All `/api/admin/**` routes already enforced by `JwtAuthFilter`; frontend middleware redirects unauthenticated users |
| IV – Data Integrity & Migration | ✅ PASS | No schema changes needed; Product table is complete |
| V – User-Centric Quote Flow | ✅ PASS | Quote flow untouched |
| VI – Simplicity & YAGNI | ✅ PASS | No new abstractions; reuses existing repo, auth, upload endpoint |

## Project Structure

### Documentation (this feature)

```text
specs/002-admin-product-management/
├── plan.md          ← this file
├── contracts/
│   └── admin-products-api.md
└── tasks.md         ← created by /speckit.tasks
```

### Source Code

```text
backend/src/main/java/com/maigepack/backend/
└── api/admin/
    ├── AdminProductController.java      ← NEW
    ├── ProductRequest.java              ← NEW (create + update DTO)
    └── ProductSummaryResponse.java      ← NEW (list view DTO)

frontend/
├── middleware/
│   └── admin-auth.ts                   ← NEW (redirect if no token)
└── pages/
    └── admin/
        ├── index.vue                   ← NEW (redirect → /admin/products)
        ├── login.vue                   ← NEW
        └── products/
            ├── index.vue               ← NEW (list + search + delete)
            ├── new.vue                 ← NEW (create form)
            └── [id]/
                └── edit.vue            ← NEW (edit form)
```

## API Contracts

### `POST /api/admin/products` — Create product

**Request** (`ProductRequest`):
```json
{
  "name": "Rigid Gift Box",
  "slug": "rigid-gift-box",
  "shortDescription": "Premium rigid box for luxury gifts",
  "description": "Full HTML/text description...",
  "category": "Rigid Boxes",
  "imageUrl": "https://cdn.example.com/img.jpg",
  "additionalImages": ["https://cdn.example.com/img2.jpg"],
  "features": ["Magnetic closure", "Custom sizing"],
  "specifications": { "Material": "Grey board", "Thickness": "2mm" },
  "materialOptions": "Grey board, Chipboard",
  "finishOptions": "Matte lamination, Gloss UV",
  "sizeRange": "100×80×50mm to 400×300×200mm",
  "minimumOrderQuantity": 100,
  "leadTime": "15–20 days",
  "targetIndustries": ["Electronics", "Cosmetics"],
  "active": true,
  "displayOrder": 0
}
```

**Validation**: `name` and `category` are `@NotBlank`. All other fields optional.
**Response** `201 Created`: full Product JSON (same shape as public API).

---

### `GET /api/admin/products` — List all products (paginated)

Query params: `page` (default 0), `size` (default 20), `search` (optional), `category` (optional), `sortBy` (default `displayOrder`), `sortDirection` (default `asc`).

**Response** `200 OK`: `Page<ProductSummaryResponse>`
```json
{
  "content": [
    { "id": 1, "name": "Rigid Gift Box", "slug": "rigid-gift-box",
      "category": "Rigid Boxes", "active": true,
      "displayOrder": 0, "createdAt": "2026-04-04T10:00:00Z" }
  ],
  "totalElements": 42,
  "totalPages": 3,
  "number": 0
}
```

---

### `GET /api/admin/products/{id}` — Get single product for edit

**Response** `200 OK`: full Product JSON. `404` if not found.

---

### `PUT /api/admin/products/{id}` — Update product

**Request**: same `ProductRequest` shape as POST.
**Response** `200 OK`: updated Product JSON. `404` if not found.

---

### `DELETE /api/admin/products/{id}` — Delete product

**Response** `204 No Content`. `404` if not found.

---

## Implementation Phases

### Phase 1 — Backend: AdminProductController

**Files to create:**
- `ProductRequest.java` — DTO with Bean Validation (`@NotBlank` on name, category)
- `ProductSummaryResponse.java` — lightweight list DTO (id, name, slug, category, active, displayOrder, createdAt)
- `AdminProductController.java` — 5 endpoints (list, get, create, update, delete)

**Key implementation notes:**
- Slug auto-generation: `name.toLowerCase().replaceAll("[^a-z0-9]+", "-")`. If slug already exists append `-2`, `-3`, etc. (loop check via `productRepository.findBySlug`).
- Admin list endpoint: add `findAllProductsWithFilters` to `ProductRepository` — same as existing `findProductsWithFilters` but **without** `active = true` so admins see inactive products.
- Map `ProductRequest` → `Product` entity manually (no MapStruct, per YAGNI).
- Return full `Product` entity for single-product responses; use `ProductSummaryResponse` for paginated list to keep payload small.

### Phase 2 — Frontend: Auth middleware + Admin pages

**`middleware/admin-auth.ts`**
- Read `adminToken` from `localStorage`.
- Decode JWT `exp` claim; if missing or expired → `navigateTo('/admin/login')`.
- Applied via `definePageMeta({ middleware: 'admin-auth' })` on all admin pages except login.

**`composables/useAdminApi.ts`**
- Thin wrapper around `$fetch` that auto-injects `Authorization: Bearer <token>` header.
- On 401 response → clear token + redirect to `/admin/login`.

**`pages/admin/login.vue`**
- Username + password form.
- `POST /api/admin/auth/login` → store token in `localStorage` as `adminToken` → `navigateTo('/admin/products')`.
- Shows "Invalid credentials" on 401.

**`pages/admin/index.vue`**
- `definePageMeta({ middleware: 'admin-auth' })` + immediate redirect to `/admin/products`.

**`pages/admin/products/index.vue`** — product list
- Table: Name, Category, Active (green/red badge), Display Order, Created At, Edit / Delete actions.
- Search input (debounced 300ms) + category filter dropdown.
- Pagination controls.
- Delete: `window.confirm()` → `DELETE /api/admin/products/{id}` → refresh.
- "Add Product" button → `/admin/products/new`.
- Logout button → clear `localStorage` → `/admin/login`.

**`pages/admin/products/new.vue`** — create form
- All `ProductRequest` fields as form inputs.
- Image upload widget: file `<input>` → `POST /api/admin/uploads` (with Bearer token) → populate `imageUrl`; show `<img>` preview.
- Dynamic add/remove rows for: features, additionalImages, targetIndustries.
- Key-value pair editor for specifications (add/remove rows).
- Client-side required check on name + category before submitting.
- On save: `POST /api/admin/products` → `/admin/products` on success.

**`pages/admin/products/[id]/edit.vue`** — edit form
- `onMounted`: `GET /api/admin/products/{id}` → pre-populate form.
- Same form component layout as new.vue.
- On save: `PUT /api/admin/products/{id}` → `/admin/products` on success.

### Phase 3 — Wiring & verification

- Smoke test locally: login → create product → confirm it appears on public `/products`.
- Confirm inactive product does NOT appear on public catalogue.
- Push branch → open PR to `develop`.

## Complexity Tracking

No constitution violations. No new abstractions beyond what the spec requires.
