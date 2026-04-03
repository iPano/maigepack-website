<!--
SYNC IMPACT REPORT
==================
Version change: 1.0.0 → 1.1.0
Added sections:
  - Core Principles (I–VI)
  - Technology Stack
  - Development Workflow
  - Governance
Modified principles:
  - Development Workflow section expanded with branch strategy (develop-first)
Added sections: n/a
Removed sections: n/a
Templates reviewed:
  - .specify/templates/plan-template.md  ✅ compatible
  - .specify/templates/spec-template.md  ✅ compatible
  - .specify/templates/tasks-template.md ✅ compatible
Follow-up TODOs:
  - TODO(RATIFICATION_DATE): Still set to first commit date 2026-04-03; update if needed.
  - TODO(UPLOAD_PROVIDER): Decide production upload provider (local vs Alibaba OSS).
  - TODO(EMAIL_POLICY): Confirm app.notification.toEmail target and staging behavior.
-->

# MaigePack Constitution

## Core Principles

### I. API Contract Stability (NON-NEGOTIABLE)

Public REST endpoints are a contract with the frontend and external consumers.
Breaking changes MUST NOT be introduced without a versioned migration path.

- Public endpoints (`/api/public/**`) MUST remain backwards-compatible within a major version.
- Any field removal, rename, or type change on a public response MUST be treated as a breaking change.
- New endpoints or additive fields MAY be added without a version bump.
- Admin endpoints (`/api/admin/**`) follow the same rules but MAY evolve faster with internal notice.
- API shapes MUST be documented before implementation; contracts drive development, not the reverse.

**Rationale**: The Nuxt 3 frontend consumes these endpoints directly. Silent contract breaks cause
runtime failures that are invisible at compile time.

### II. Design System Authority

The Tailwind CSS configuration (`frontend/tailwind.config.js`) is the single source of truth
for all visual tokens. No hardcoded hex values, spacing values, or font stacks are permitted
outside this file.

- All components MUST use design tokens (`primary-600`, `navy-900`, `accent-500`, etc.).
- Brand colors MUST NOT be overridden inline or in scoped `<style>` blocks.
- Typography MUST use the defined font stacks: Inter (sans), Merriweather (serif), JetBrains Mono (mono).
- New design tokens MUST be added to `tailwind.config.js` before use — never invented ad-hoc.
- Mobile-first responsive design is REQUIRED; desktop-only layouts are not acceptable.

**Rationale**: Visual consistency is a core product requirement for a B2B packaging brand.
Drift between components erodes trust with business customers.

### III. Security Boundaries

Authentication and authorization rules MUST be enforced at the framework layer, not in
application logic.

- All `/api/admin/**` routes MUST be protected by JWT authentication via `JwtAuthFilter`.
- JWT secrets MUST be supplied via environment variables — never hardcoded or committed.
- JWT tokens expire after 24 hours; refresh or re-authentication is required beyond that.
- Public routes (`/api/public/**`) MUST NOT require authentication.
- Input validation MUST use Bean Validation (`@Valid`, `@NotBlank`, etc.) on all request DTOs.
- File uploads MUST validate MIME type and enforce a maximum file size before storage.

**Rationale**: Admin endpoints control product data and media assets. Accidental exposure would
allow unauthorized content modification on the public site.

### IV. Data Integrity & Migration

The database schema is the source of truth for data structure. All schema changes
MUST go through Flyway migrations.

- Every schema change MUST have a corresponding Flyway migration script in `backend/src/main/resources/db/migration/`.
- Migrations MUST be forward-only; rollback scripts are not required but breakpoint notes are encouraged.
- Production database is PostgreSQL; H2 is permitted only in local development and tests.
- JPA entities MUST NOT use `ddl-auto: create` or `ddl-auto: update` in any non-local profile.
- Database credentials MUST be supplied via environment variables.

**Rationale**: Uncontrolled schema drift causes data loss and deployment failures. Flyway
ensures all environments stay synchronized with the application version.

### V. User-Centric Quote Flow

The quote request form is the primary business conversion point. Its reliability and
usability are non-negotiable.

- The `/api/public/forms/quote` endpoint MUST persist every valid submission to the database
  before attempting email notification.
- Email notification failure MUST NOT cause the quote submission to fail or return an error
  to the user — it MUST be gracefully handled (log + continue).
- Form validation errors MUST return clear, field-level messages (HTTP 400 with structured body).
- The frontend quote form MUST be accessible: proper labels, keyboard navigation, and ARIA roles.
- Quote submission MUST complete (persist + respond) within 3 seconds under normal load.

**Rationale**: A lost quote = lost business. The system must be resilient even when downstream
services (email) are degraded.

### VI. Simplicity & YAGNI

Complexity must be justified by a current, concrete need — not anticipated future requirements.

- New abstractions (services, patterns, layers) MUST solve a problem that exists today.
- The number of active Spring beans, Vue components, and Nuxt pages SHOULD be kept to a minimum
  that satisfies the feature.
- The Alibaba OSS storage service MUST NOT be enabled in production until the feature is fully
  implemented and tested; the local storage provider is the default until then.
- Dependencies MUST NOT be added without explicit justification in the PR description.

**Rationale**: This is a focused marketing + catalogue site. Over-engineering increases maintenance
burden and onboarding cost without proportional benefit.

## Technology Stack

### Frontend

- **Framework**: Nuxt 3 (`^3.13.2`) with Vue 3 (`^3.4.0`), SSR disabled (SPA mode).
- **Styling**: Tailwind CSS (`^3.4.19`) via `@nuxtjs/tailwindcss` — see Principle II.
- **Language**: TypeScript (`^5.0.0`) throughout; plain JS files are not acceptable in new code.
- **API base**: Configured via `NUXT_PUBLIC_API_BASE_URL` runtime env var (default: `http://localhost:8081`).
- **Routing**: Nuxt file-based routing; `<NuxtLink>` MUST be used for all internal navigation
  (see the `Button.vue` `computedTag` pattern).

### Backend

- **Framework**: Spring Boot `3.2.5` on Java 17 (toolchain-enforced).
- **Build**: Gradle with the Spring Boot plugin.
- **Persistence**: Spring Data JPA + Flyway; PostgreSQL (prod), H2 (dev/test).
- **Security**: Spring Security + custom `JwtAuthFilter` + `JwtService` (HMAC-SHA256, 24h expiry).
- **Email**: Spring Mail (`spring-boot-starter-mail`); graceful no-op when unconfigured.
- **Storage**: pluggable `MediaStorageService`; default is `LocalMediaStorageService`.

## Development Workflow

### Quality Gates (in order)

1. **Spec first**: Features MUST have a `spec.md` in `.specify/specs/` before implementation begins.
2. **Plan before code**: A `plan.md` with a Constitution Check section MUST be approved before tasks begin.
3. **Contract before implementation**: API contracts (request/response shapes) MUST be defined in `contracts/` before backend work starts.
4. **Tests for critical paths**: Unit and integration tests are REQUIRED for:
   - Product query/filter logic (`ProductRepository.findProductsWithFilters`)
   - Quote submission persistence and email fallback
   - JWT authentication filter
5. **No broken builds**: PRs MUST pass Gradle build (`./gradlew build`) and Nuxt build (`npm run build`) before merge.
6. **Design token compliance**: UI PRs MUST be reviewed for design token adherence (Principle II).

### Branch Strategy

- `main` is the production-ready branch. Direct commits to `main` are **not permitted** for features or fixes.
- `develop` is the integration branch. **All work MUST be pushed to `develop` first.**
- Feature branches follow spec-kit naming: `###-feature-name` (e.g., `001-product-detail-page`).
- Feature branches are merged into `develop` via pull request; `develop` → `main` when ready to release.
- Hotfixes MAY be committed directly to `develop` with a co-author note; they reach `main` via the normal PR flow unless a production incident demands an emergency `main` fix.
- A push guard hook (`.claude/settings.json`) blocks accidental direct pushes to `main` from Claude Code.

### Environment Variables (required, never committed)

| Variable | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection string |
| `SPRING_DATASOURCE_USERNAME` | DB user |
| `SPRING_DATASOURCE_PASSWORD` | DB password |
| `APP_JWT_SECRET` | JWT signing secret (min 32 chars) |
| `APP_NOTIFICATION_TO_EMAIL` | Quote notification recipient |
| `NUXT_PUBLIC_API_BASE_URL` | Backend base URL for frontend |

## Governance

This constitution supersedes all other project guidelines. When a conflict exists between
this document and a PR, ticket, or verbal agreement, this document wins.

### Amendment Procedure

1. Open a PR modifying `.specify/memory/constitution.md` with a clear rationale.
2. Increment `CONSTITUTION_VERSION` per semantic versioning rules (see header comment).
3. Run the `/speckit.constitution` command to propagate changes to dependent templates.
4. PR MUST be reviewed and approved before merge.
5. Update `LAST_AMENDED_DATE` to the merge date.

### Versioning Policy

- **MAJOR**: Removal or redefinition of a principle; breaking governance change.
- **MINOR**: New principle or section added; materially expanded guidance.
- **PATCH**: Clarifications, wording fixes, non-semantic refinements.

### Compliance

- All PRs must be checked against this constitution by the author before requesting review.
- Violations must be documented in the PR description with justification or resolved before merge.
- The `Complexity Tracking` table in `plan.md` is the mechanism for justified exceptions.

**Version**: 1.1.0 | **Ratified**: 2026-04-03 | **Last Amended**: 2026-04-03
