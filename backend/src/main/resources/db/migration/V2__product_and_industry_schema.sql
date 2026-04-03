-- Product and Industry tables with all ElementCollection join tables.
-- Flyway is now enabled in production; this migration brings the schema
-- up to date for any Railway PostgreSQL instance that was previously
-- managed by Hibernate ddl-auto=update (which may have missed join tables).

-- ───────────────────────────────────────────────
-- PRODUCT
-- ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS product (
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(128)  NOT NULL,
    slug             VARCHAR(256)  NOT NULL UNIQUE,
    description      TEXT          NOT NULL,
    short_description VARCHAR(1024),
    category         VARCHAR(64)   NOT NULL,
    image_url        VARCHAR(512),
    material_options VARCHAR(256),
    finish_options   VARCHAR(256),
    size_range       VARCHAR(256),
    minimum_order_quantity INTEGER,
    lead_time        VARCHAR(64),
    active           BOOLEAN       NOT NULL DEFAULT TRUE,
    display_order    INTEGER       NOT NULL DEFAULT 0,
    created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS product_images (
    product_id BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    image_url  VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS product_features (
    product_id BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    feature    TEXT
);

CREATE TABLE IF NOT EXISTS product_specifications (
    product_id BIGINT       NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    spec_name  VARCHAR(255) NOT NULL,
    spec_value TEXT,
    PRIMARY KEY (product_id, spec_name)
);

CREATE TABLE IF NOT EXISTS product_industries (
    product_id BIGINT       NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    industry   VARCHAR(255)
);

-- ───────────────────────────────────────────────
-- INDUSTRY
-- ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS industry (
    id                       BIGSERIAL PRIMARY KEY,
    name                     VARCHAR(128)  NOT NULL,
    slug                     VARCHAR(256)  NOT NULL UNIQUE,
    description              TEXT,
    short_description        VARCHAR(1024),
    hero_image_url           VARCHAR(512),
    icon_url                 VARCHAR(256),
    case_studies             TEXT,
    meta_title               VARCHAR(256),
    meta_description         VARCHAR(512),
    active                   BOOLEAN  NOT NULL DEFAULT TRUE,
    display_order            INTEGER  NOT NULL DEFAULT 0,
    created_at               TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at               TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS industry_key_benefits (
    industry_id BIGINT NOT NULL REFERENCES industry(id) ON DELETE CASCADE,
    benefit     TEXT
);

CREATE TABLE IF NOT EXISTS industry_common_challenges (
    industry_id BIGINT NOT NULL REFERENCES industry(id) ON DELETE CASCADE,
    challenge   TEXT
);

CREATE TABLE IF NOT EXISTS industry_packaging_solutions (
    industry_id BIGINT NOT NULL REFERENCES industry(id) ON DELETE CASCADE,
    solution    TEXT
);

CREATE TABLE IF NOT EXISTS industry_recommended_product_types (
    industry_id          BIGINT NOT NULL REFERENCES industry(id) ON DELETE CASCADE,
    recommended_product  VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS industry_seo_keywords (
    industry_id BIGINT NOT NULL REFERENCES industry(id) ON DELETE CASCADE,
    keyword     VARCHAR(256)
);
