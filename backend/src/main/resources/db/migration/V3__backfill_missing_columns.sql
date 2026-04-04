-- Backfill columns that were added to entities after V1 was written.
-- The Railway DB was previously managed by ddl-auto=update which added
-- these columns in-place; Flyway was not tracking them. This migration
-- makes the schema match the current JPA entity definitions so that
-- ddl-auto=validate passes cleanly.
--
-- All statements use ADD COLUMN IF NOT EXISTS so this is safe to re-run
-- or apply against a fresh DB created by V1 + V2.

-- ───────────────────────────────────────────────
-- form_submission — extra quote-form fields added after initial launch
-- ───────────────────────────────────────────────
ALTER TABLE form_submission
    ADD COLUMN IF NOT EXISTS company              VARCHAR(128),
    ADD COLUMN IF NOT EXISTS product_type         VARCHAR(64),
    ADD COLUMN IF NOT EXISTS quantity             VARCHAR(128),
    ADD COLUMN IF NOT EXISTS industry             VARCHAR(64),
    ADD COLUMN IF NOT EXISTS timeline             VARCHAR(128),
    ADD COLUMN IF NOT EXISTS material_preferences VARCHAR(256),
    ADD COLUMN IF NOT EXISTS finish_preferences   VARCHAR(256),
    ADD COLUMN IF NOT EXISTS size_requirements    VARCHAR(256),
    ADD COLUMN IF NOT EXISTS status               VARCHAR(16)  NOT NULL DEFAULT 'pending',
    ADD COLUMN IF NOT EXISTS assigned_to          VARCHAR(256),
    ADD COLUMN IF NOT EXISTS admin_notes          TEXT,
    ADD COLUMN IF NOT EXISTS reviewed_at          TIMESTAMP;

-- created_at in V1 was TIMESTAMP DEFAULT NOW(); entity uses Instant (mapped
-- to TIMESTAMP). Column already exists — no change needed.

-- ───────────────────────────────────────────────
-- media_object — created_at type alignment
-- ───────────────────────────────────────────────
-- V1 omitted created_at from media_object; add it if missing.
ALTER TABLE media_object
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT NOW();
